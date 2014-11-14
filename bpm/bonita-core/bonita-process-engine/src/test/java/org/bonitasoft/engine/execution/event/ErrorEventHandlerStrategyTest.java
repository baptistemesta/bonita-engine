package org.bonitasoft.engine.execution.event;

import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.bonitasoft.engine.core.process.definition.ProcessDefinitionService;
import org.bonitasoft.engine.core.process.definition.model.SProcessDefinition;
import org.bonitasoft.engine.core.process.definition.model.event.impl.SBoundaryEventDefinitionImpl;
import org.bonitasoft.engine.core.process.definition.model.event.trigger.impl.SCatchErrorEventTriggerDefinitionImpl;
import org.bonitasoft.engine.core.process.definition.model.impl.SFlowElementContainerDefinitionImpl;
import org.bonitasoft.engine.core.process.definition.model.impl.SProcessDefinitionImpl;
import org.bonitasoft.engine.core.process.definition.model.impl.SStandardLoopCharacteristicsImpl;
import org.bonitasoft.engine.core.process.definition.model.impl.SUserTaskDefinitionImpl;
import org.bonitasoft.engine.core.process.instance.api.FlowNodeInstanceService;
import org.bonitasoft.engine.core.process.instance.api.ProcessInstanceService;
import org.bonitasoft.engine.core.process.instance.api.event.EventInstanceService;
import org.bonitasoft.engine.core.process.instance.model.STaskPriority;
import org.bonitasoft.engine.core.process.instance.model.event.impl.SEndEventInstanceImpl;
import org.bonitasoft.engine.core.process.instance.model.impl.SMultiInstanceActivityInstanceImpl;
import org.bonitasoft.engine.core.process.instance.model.impl.SUserTaskInstanceImpl;
import org.bonitasoft.engine.execution.ContainerRegistry;
import org.bonitasoft.engine.log.technical.TechnicalLoggerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ErrorEventHandlerStrategyTest {

    @Mock
    private EventInstanceService eventInstanceService;

    @Mock
    private ProcessInstanceService processInstanceService;

    @Mock
    private FlowNodeInstanceService flowNodeInstanceService;

    @Mock
    private ContainerRegistry containerRegistry;

    @Mock
    private ProcessDefinitionService processDefinitionService;

    @Mock
    private EventsHandler eventsHandler;

    @Mock
    private TechnicalLoggerService logger;

    @InjectMocks
    @Spy
    private ErrorEventHandlerStrategy strategy;

    @Test
    public void getWaitingErrorEventFromBoundary_should_find_matching_event_in_multiple_instance_activity() throws Exception {
        final long processDefinitionId = 7845475L;
        final long flowNodeDefinitionId = 357L;
        final long rootContainerId = 951L;
        final long parentContainerId = 7887L;
        final long logicalGroup2 = 78454646L;
        final long parentActivityInstanceId = 7897L;
        final String errorCode = "mistake";
        final SProcessDefinition definition = buildProcessDefinitionWithAMultipleInstanceUserTask(flowNodeDefinitionId, errorCode);
        final SMultiInstanceActivityInstanceImpl multiInstanceInstance = buildMultiInstanceActivity(processDefinitionId, flowNodeDefinitionId, rootContainerId,
                parentContainerId);
        final SEndEventInstanceImpl eventInstance = buildEventInstance(processDefinitionId, flowNodeDefinitionId, rootContainerId, parentContainerId,
                logicalGroup2);
        final SUserTaskInstanceImpl instance = buildSUserTaskInstance(processDefinitionId, flowNodeDefinitionId, rootContainerId, parentContainerId,
                logicalGroup2, parentActivityInstanceId);
        when(processDefinitionService.getProcessDefinition(processDefinitionId)).thenReturn(definition);
        when(flowNodeInstanceService.getFlowNodeInstance(parentActivityInstanceId)).thenReturn(multiInstanceInstance);

        strategy.getWaitingErrorEventFromBoundary(eventInstance, errorCode, instance);

        verify(strategy).getWaitingErrorEventFromBoundary(eq(errorCode), eq(multiInstanceInstance), anyList());
    }

    private SMultiInstanceActivityInstanceImpl buildMultiInstanceActivity(final long processDefinitionId, final long flowNodeDefinitionId,
            final long rootContainerId,
            final long parentContainerId) {
        final SMultiInstanceActivityInstanceImpl multiInstanceInstance = new SMultiInstanceActivityInstanceImpl("MIActivity", flowNodeDefinitionId,
                rootContainerId, parentContainerId, processDefinitionId, rootContainerId, false);
        return multiInstanceInstance;
    }

    private SUserTaskInstanceImpl buildSUserTaskInstance(final long processDefinitionId, final long flowNodeDefinitionId, final long rootContainerId,
            final long parentContainerId, final long logicalGroup2, final long parentActivityInstanceId) {
        final SUserTaskInstanceImpl instance = new SUserTaskInstanceImpl("usertTask", flowNodeDefinitionId, rootContainerId, parentContainerId, 457L,
                STaskPriority.NORMAL, processDefinitionId, logicalGroup2);
        instance.setLogicalGroup(2, parentActivityInstanceId);
        return instance;
    }

    private SEndEventInstanceImpl buildEventInstance(final long processDefinitionId, final long flowNodeDefinitionId, final long rootContainerId,
            final long parentContainerId, final long logicalGroup2) {
        final SEndEventInstanceImpl eventInstance = new SEndEventInstanceImpl("ErrorEvent", flowNodeDefinitionId, rootContainerId, parentContainerId,
                processDefinitionId, logicalGroup2);
        eventInstance.setLogicalGroup(2, 9L);
        return eventInstance;
    }

    private SProcessDefinition buildProcessDefinitionWithAMultipleInstanceUserTask(final long flowNodeDefinitionId, final String errorCode) {
        final SProcessDefinitionImpl definition = new SProcessDefinitionImpl("test", "2.0");
        final SStandardLoopCharacteristicsImpl loopCharacteristics = new SStandardLoopCharacteristicsImpl(null, true);
        final SUserTaskDefinitionImpl taskDefinition = new SUserTaskDefinitionImpl(flowNodeDefinitionId, "usertTask", "employee");
        taskDefinition.setLoopCharacteristics(loopCharacteristics);
        final SBoundaryEventDefinitionImpl boundaryEventDefinition = new SBoundaryEventDefinitionImpl(786768768L, "error");
        final SCatchErrorEventTriggerDefinitionImpl errorEventTrigger = new SCatchErrorEventTriggerDefinitionImpl(errorCode);
        boundaryEventDefinition.addErrorEventTrigger(errorEventTrigger);
        taskDefinition.addBoundaryEventDefinition(boundaryEventDefinition);
        final SFlowElementContainerDefinitionImpl processContainer = new SFlowElementContainerDefinitionImpl();
        processContainer.addActivity(taskDefinition);
        definition.setProcessContainer(processContainer);
        return definition;
    }

}
