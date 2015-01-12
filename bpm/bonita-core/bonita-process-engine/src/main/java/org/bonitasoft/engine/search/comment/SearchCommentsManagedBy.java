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
package org.bonitasoft.engine.search.comment;

import java.util.List;

import org.bonitasoft.engine.core.process.comment.api.SCommentService;
import org.bonitasoft.engine.core.process.comment.model.SComment;
import org.bonitasoft.engine.persistence.QueryOptions;
import org.bonitasoft.engine.persistence.SBonitaReadException;
import org.bonitasoft.engine.search.AbstractCommentSearchEntity;
import org.bonitasoft.engine.search.SearchOptions;
import org.bonitasoft.engine.search.descriptor.SearchEntityDescriptor;

/**
 * @author Hongwen Zang
 */
public class SearchCommentsManagedBy extends AbstractCommentSearchEntity {

    private final SCommentService commentService;

    private final long managerUserId;

    public SearchCommentsManagedBy(SearchEntityDescriptor searchDescriptor, SearchOptions options, SCommentService commentService, long managerUserId) {
        super(searchDescriptor, options);
        this.commentService = commentService;
        this.managerUserId = managerUserId;
    }

    @Override
    public long executeCount(QueryOptions searchOptions) throws SBonitaReadException {
        return commentService.getNumberOfCommentsManagedBy(managerUserId, searchOptions);
    }

    @Override
    public List<SComment> executeSearch(QueryOptions searchOptions) throws SBonitaReadException {
        return commentService.searchCommentsManagedBy(managerUserId, searchOptions);
    }

}
