/**
 * Copyright (C) 2012 BonitaSoft S.A.
 * BonitaSoft, 32 rue Gustave Eiffel - 38000 Grenoble
 * This library is free software; you can redistribute it and/or modify it under the terms
 * of the GNU Lesser General Public License as published by the Free Software Foundation
 * version 2.1 of the License.
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 * You should have received a copy of the GNU Lesser General Public License along with this
 * program; if not, write to the Free Software Foundation, Inc., 51 Franklin Street, Fifth
 * Floor, Boston, MA 02110-1301, USA.
 **/
package org.bonitasoft.engine.execution.state;

import org.bonitasoft.engine.commons.exceptions.SBonitaException;
import org.bonitasoft.engine.core.process.definition.model.SProcessDefinition;
import org.bonitasoft.engine.core.process.instance.api.ActivityInstanceService;
import org.bonitasoft.engine.core.process.instance.api.TokenService;
import org.bonitasoft.engine.core.process.instance.api.exceptions.SActivityExecutionException;
import org.bonitasoft.engine.core.process.instance.api.exceptions.SActivityStateExecutionException;
import org.bonitasoft.engine.core.process.instance.api.states.FlowNodeState;
import org.bonitasoft.engine.core.process.instance.api.states.StateCode;
import org.bonitasoft.engine.core.process.instance.model.SActivityInstance;
import org.bonitasoft.engine.core.process.instance.model.SFlowElementsContainerType;
import org.bonitasoft.engine.core.process.instance.model.SFlowNodeInstance;
import org.bonitasoft.engine.core.process.instance.model.SStateCategory;
import org.bonitasoft.engine.core.process.instance.model.SToken;
import org.bonitasoft.engine.core.process.instance.model.builder.BPMInstanceBuilders;
import org.bonitasoft.engine.core.process.instance.model.builder.SFlowNodeInstanceBuilder;
import org.bonitasoft.engine.core.process.instance.model.event.SBoundaryEventInstance;
import org.bonitasoft.engine.execution.ContainerRegistry;

/**
 * @author Elias Ricken de Medeiros
 */
public class ExecutingBoundaryEventStateImpl implements FlowNodeState {

    private final ActivityInstanceService activityInstanceService;

    private final ContainerRegistry containerRegistry;

    private final BPMInstanceBuilders bpmInstanceBuilders;

    private final TokenService tokenService;

    public ExecutingBoundaryEventStateImpl(final ActivityInstanceService activityInstanceService, final ContainerRegistry containerRegistry,
            final BPMInstanceBuilders bpmInstanceBuilders, TokenService tokenService) {
        this.activityInstanceService = activityInstanceService;
        this.containerRegistry = containerRegistry;
        this.bpmInstanceBuilders = bpmInstanceBuilders;
        this.tokenService = tokenService;
    }

    @Override
    public boolean shouldExecuteState(final SProcessDefinition processDefinition, final SFlowNodeInstance flowNodeInstance) throws SActivityExecutionException {
        return true;
    }

    @Override
    public boolean mustAddSystemComment(final SFlowNodeInstance flowNodeInstance) {
        return false;
    }

    @Override
    public String getSystemComment(final SFlowNodeInstance flowNodeInstance) {
        return "executing boundary event";
    }

    @Override
    public StateCode execute(final SProcessDefinition processDefinition, final SFlowNodeInstance instance) throws SActivityStateExecutionException {
        final SBoundaryEventInstance boundaryEventInstance = (SBoundaryEventInstance) instance;
        if (boundaryEventInstance.isInterrupting()) {
            aborteRelatedActivity(boundaryEventInstance);
        }
        // we create token here to be sure the token is put synchronously
        createToken(boundaryEventInstance);

        return StateCode.DONE;
    }

    private void createToken(final SBoundaryEventInstance boundaryEventInstance) throws SActivityStateExecutionException {
        // we have always 1 outgoing transition with no condition so we create one token
        Long outputTokenRefId;
        Long outputParentTokenRefId;
        try {
            if (boundaryEventInstance.isInterrupting()) {
                // we create the same token that activated the activity
                // the activity is canceled so a token will be consumed by the aborted activity
                final SToken token = tokenService.getToken(boundaryEventInstance.getParentProcessInstanceId(), boundaryEventInstance.getTokenRefId());
                outputTokenRefId = token.getRefId();
                outputParentTokenRefId = token.getParentRefId();
            } else {
                // a token with no parent is produced -> not the same "execution" than activity
                outputTokenRefId = boundaryEventInstance.getId();
                outputParentTokenRefId = null;
            }
            tokenService.createTokens(boundaryEventInstance.getParentProcessInstanceId(), outputTokenRefId, outputParentTokenRefId, 1);
        } catch (SBonitaException e) {
            throw new SActivityStateExecutionException(e);
        }
    }

    private void aborteRelatedActivity(final SBoundaryEventInstance boundaryEventInstance) throws SActivityStateExecutionException {
        final SFlowNodeInstanceBuilder flowNodeKeyProvider = bpmInstanceBuilders.getSUserTaskInstanceBuilder();
        if (SStateCategory.NORMAL.equals(boundaryEventInstance.getStateCategory())) {
            try {
                final SActivityInstance relatedActivityInst = activityInstanceService.getActivityInstance(boundaryEventInstance.getActivityInstanceId());
                activityInstanceService.setStateCategory(relatedActivityInst, SStateCategory.ABORTING);
                activityInstanceService.setAbortedByBoundaryEvent(relatedActivityInst, boundaryEventInstance.getId());
                if (relatedActivityInst.isStable() || relatedActivityInst.isStateExecuting()) {
                    String containerType = SFlowElementsContainerType.PROCESS.name();
                    final long parentActivityInstanceId = relatedActivityInst.getParentActivityInstanceId();
                    if (parentActivityInstanceId > 0) {
                        containerType = SFlowElementsContainerType.FLOWNODE.name();
                    }
                    containerRegistry.executeFlowNode(relatedActivityInst.getId(), null, null, containerType,
                            boundaryEventInstance.getLogicalGroup(flowNodeKeyProvider.getParentProcessInstanceIndex()));
                }
            } catch (final SBonitaException e) {
                throw new SActivityStateExecutionException(e);
            }
        }
    }

    @Override
    public boolean hit(final SProcessDefinition processDefinition, final SFlowNodeInstance parentInstance, final SFlowNodeInstance childInstance)
            throws SActivityStateExecutionException {
        return true;
    }

    @Override
    public int getId() {
        return 65;
    }

    @Override
    public String getName() {
        return "executing";
    }

    @Override
    public boolean isInterrupting() {
        return false;
    }

    @Override
    public boolean isStable() {
        return false;
    }

    @Override
    public boolean isTerminal() {
        return false;
    }

    @Override
    public SStateCategory getStateCategory() {
        return SStateCategory.NORMAL;
    }

}
