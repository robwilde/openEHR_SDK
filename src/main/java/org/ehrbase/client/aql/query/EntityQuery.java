/*
 *
 *  *  Copyright (c) 2020  Stefan Spiska (Vitasystems GmbH) and Hannover Medical School
 *  *  This file is part of Project EHRbase
 *  *
 *  *  Licensed under the Apache License, Version 2.0 (the "License");
 *  *  you may not use this file except in compliance with the License.
 *  *  You may obtain a copy of the License at
 *  *
 *  *  http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  *  Unless required by applicable law or agreed to in writing, software
 *  *  distributed under the License is distributed on an "AS IS" BASIS,
 *  *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  *  See the License for the specific language governing permissions and
 *  *  limitations under the License.
 *
 */

package org.ehrbase.client.aql.query;

import org.ehrbase.client.aql.condition.Condition;
import org.ehrbase.client.aql.containment.Containment;
import org.ehrbase.client.aql.containment.ContainmentExpression;
import org.ehrbase.client.aql.field.AqlField;
import org.ehrbase.client.aql.field.SelectAqlField;
import org.ehrbase.client.aql.parameter.Parameter;
import org.ehrbase.client.aql.record.Record;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class EntityQuery<T extends Record> implements Query<T> {
    private final SelectAqlField<?>[] fields;
    private final ContainmentExpression containmentExpression;
    private int variabelCount = 0;
    private int parameterCount = 0;
    private Map<Containment, String> variablesMap = new HashMap<>();
    private Condition where;

    protected EntityQuery(ContainmentExpression containmentExpression, SelectAqlField<?>... fields) {
        this.fields = fields;
        this.containmentExpression = containmentExpression;
        containmentExpression.bindQuery(this);
    }


    @Override
    public String buildAql() {
        StringBuilder sb = new StringBuilder();
        sb
                .append("Select ")
                .append(Arrays.stream(fields).map(SelectAqlField::buildAQL).collect(Collectors.joining(", ")))
                .append(" from EHR e ");
        if (containmentExpression != null) {
            sb
                    .append("contains ")
                    .append(containmentExpression.buildAQL());
        }
        if (where != null) {
            sb
                    .append(" where ")
                    .append(where.buildAql());
        }
        return sb.toString();
    }

    @Override
    public AqlField<?>[] fields() {
        return fields;
    }

    public String buildVariabelName(Containment containment) {

        return variablesMap.computeIfAbsent(containment, this::buildVariablesNameIntern);
    }

    private String buildVariablesNameIntern(Containment containment) {
        String name = containment.getType().getSimpleName().substring(0, 1).toLowerCase() + variabelCount;
        variabelCount++;
        return name;
    }

    public EntityQuery<T> where(Condition where) {
        this.where = where;
        return this;
    }

    public String buildParameterName() {
        String name = "parm" + parameterCount;
        parameterCount++;
        return name;
    }

    public <V> Parameter<V> buildParameter() {
        return new Parameter<>(this);
    }
}
