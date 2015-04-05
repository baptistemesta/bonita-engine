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
 */
package org.bonitasoft.engine.bpm;

/**
 * Interface <code>NamedElement</code> names a <code>BonitaObject</code>.
 *
 * @author Baptiste Mesta
 * @author Matthieu Chaffotte
 * @since 6.0.0
 */
public abstract class NamedElement extends BaseElement {
    private static final long serialVersionUID = 2L;

    private String name;

    public NamedElement(final String name) {
        this.name = name;
    }


    /**
     * Gets the element name.
     *
     * @return the element name
     */
    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }


}
