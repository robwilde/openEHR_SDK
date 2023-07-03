/*
 * Copyright (c) 2023 vitasystems GmbH and Hannover Medical School.
 *
 * This file is part of project openEHR_SDK
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.ehrbase.openehr.sdk.aql.dto.operand;

import java.util.ArrayList;
import java.util.List;
import org.ehrbase.openehr.sdk.aql.dto.containment.AbstractContainmentExpression;
import org.ehrbase.openehr.sdk.aql.dto.path.AndOperatorPredicate;
import org.ehrbase.openehr.sdk.aql.dto.path.AqlObjectPath;

/**
 * @author Stefan Spiska
 */
public class IdentifiedPath implements ColumnExpression, Operand, ComparisonLeftOperand {

    private AbstractContainmentExpression from;

    private List<AndOperatorPredicate> rootPredicate;

    private AqlObjectPath path;

    public AbstractContainmentExpression getFrom() {
        return from;
    }

    public void setFrom(AbstractContainmentExpression from) {
        this.from = from;
    }

    public List<AndOperatorPredicate> getRootPredicate() {
        return rootPredicate;
    }

    public void setRootPredicate(List<AndOperatorPredicate> rootPredicate) {
        this.rootPredicate = new ArrayList<>(rootPredicate);
    }

    public AqlObjectPath getPath() {
        return path;
    }

    public void setPath(AqlObjectPath path) {
        this.path = path;
    }
}
