/**
 * Copyright (C) 2015 BonitaSoft S.A.
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
package org.bonitasoft.engine.bpm.flownode;

import org.bonitasoft.engine.bpm.ArchivedElement;
import org.bonitasoft.engine.bpm.BaseElement;
import org.bonitasoft.engine.bpm.BaseElementInstance;
import org.bonitasoft.engine.bpm.DescriptionElement;
import org.bonitasoft.engine.bpm.DescriptionElementInstance;

/**
 * @author Emmanuel Duchastenier
 * @author Matthieu Chaffotte
 */
public interface ArchivedFlowElementInstance extends DescriptionElementInstance, BaseElementInstance, ArchivedElement {

    long getRootContainerId();

    long getParentContainerId();

    /**
     * Is this flow element instance currently aborting?
     * 
     * @return true if this flow element instance is currently aborting, false otherwise
     * @since 6.0
     */
    boolean isAborting();

}
