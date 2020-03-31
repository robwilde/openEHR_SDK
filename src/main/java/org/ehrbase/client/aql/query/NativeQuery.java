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

import org.ehrbase.client.aql.field.AqlField;
import org.ehrbase.client.aql.record.Record;

public class NativeQuery<T extends Record> implements Query<T> {
    private final AqlField<?>[] aqlFields;
    private final String aql;

    protected NativeQuery(String aql, AqlField<?>... aqlFields) {
        this.aqlFields = aqlFields;
        this.aql = aql;
    }

    @Override
    public String buildAql() {
        return aql;
    }

    @Override
    public AqlField<?>[] fields() {
        return aqlFields;
    }
}


