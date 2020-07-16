/*
 * Copyright (c) 2019 Vitasystems GmbH and Christian Chevalley (Hannover Medical School).
 *
 * This file is part of project EHRbase
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.ehrbase.validation.terminology.validator;

import org.ehrbase.terminology.openehr.TerminologyInterface;
import org.ehrbase.terminology.openehr.implementation.AttributeCodesetMapping;

public class IntervalEvent extends TerminologyCheck {

    public IntervalEvent() {
        this.RM_CLASS = com.nedap.archie.rm.datastructures.IntervalEvent.class;
    }

    public static void check(TerminologyInterface terminologyInterface, AttributeCodesetMapping codesetMapping, String context, com.nedap.archie.rm.datastructures.IntervalEvent intervalEvent, String language) throws IllegalArgumentException {
        if (intervalEvent.getMathFunction() != null)
            validate(terminologyInterface, codesetMapping, "mathFunction", intervalEvent.getMathFunction().getDefiningCode(), language);
    }

    public static void check(TerminologyInterface terminologyInterface, AttributeCodesetMapping codesetMapping, String context, com.nedap.archie.rm.datastructures.IntervalEvent intervalEvent) throws IllegalArgumentException {
        check(terminologyInterface, codesetMapping, context, intervalEvent, "en");
    }

}