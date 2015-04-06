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

import java.util.Objects;

/**
 * Interface <code>BaseElement</code> identifies a <code>BonitaObject</code>.
 *
 * @author Baptiste Mesta
 * @author Emmanuel Duchastenier
 * @author Matthieu Chaffotte
 * @since 6.0.0
 */
public abstract class BaseElement implements BonitaObject {

    private static final long serialVersionUID = 2L;

    private long id;

    /**
     * Gets the unique object identifier.
     *
     * @return the unique object identifier
     */
    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BaseElement)) return false;
        BaseElement that = (BaseElement) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
