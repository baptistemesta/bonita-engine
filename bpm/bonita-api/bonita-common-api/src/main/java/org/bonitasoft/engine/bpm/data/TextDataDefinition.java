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
package org.bonitasoft.engine.bpm.data;

import org.bonitasoft.engine.expression.Expression;

/**
 * Represents a {@link DataDefinition} of text type.
 *
 * @author Feng Hui
 * @author Celine Souchet
 * @since 6.0.0
 * @version 6.4.1
 */
public class TextDataDefinition extends DataDefinition {
    private static final long serialVersionUID = 2L;

    private boolean longText;

    public TextDataDefinition(final String name, final Expression defaultValueExpression) {
        super(name, defaultValueExpression);
    }

    /**
     * Is it a long text?
     *
     * @return <code>true</code> if the data is a long text, <code>false</code> otherwise.
     * @since 6.0.0
     */
    public boolean isLongText() {
        return longText;
    }

    public void setLongText(final boolean longText) {
        this.longText = longText;
    }


}
