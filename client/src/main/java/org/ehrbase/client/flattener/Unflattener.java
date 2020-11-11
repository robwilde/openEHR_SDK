/*
 *  Copyright (c) 2019  Stefan Spiska (Vitasystems GmbH) and Hannover Medical School
 *  This file is part of Project EHRbase
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package org.ehrbase.client.flattener;

import com.nedap.archie.creation.RMObjectCreator;
import com.nedap.archie.rm.RMObject;
import com.nedap.archie.rm.composition.Composition;
import com.nedap.archie.rminfo.ArchieRMInfoLookup;
import org.ehrbase.building.OptSkeletonBuilder;
import org.ehrbase.client.annotations.Template;
import org.ehrbase.client.exception.ClientException;
import org.ehrbase.normalizer.Normalizer;
import org.ehrbase.webtemplate.templateprovider.TemplateProvider;
import org.openehr.schemas.v1.OPERATIONALTEMPLATE;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.ehrbase.client.flattener.DtoToCompositionWalker.findEntity;

public class Unflattener {

  public static final ArchieRMInfoLookup ARCHIE_RM_INFO_LOOKUP = ArchieRMInfoLookup.getInstance();
  public static final Normalizer NORMALIZER = new Normalizer();
  public static final OptSkeletonBuilder OPT_SKELETON_BUILDER = new OptSkeletonBuilder();
  private static final RMObjectCreator RM_OBJECT_CREATOR =
      new RMObjectCreator(ARCHIE_RM_INFO_LOOKUP);
  private final Logger logger = LoggerFactory.getLogger(this.getClass());
  private final TemplateProvider templateProvider;

  public Unflattener(TemplateProvider templateProvider) {

    this.templateProvider = templateProvider;
  }

  public RMObject unflatten(Object dto) {
    Template template = dto.getClass().getAnnotation(Template.class);

    OPERATIONALTEMPLATE operationalTemplate =
        templateProvider
            .find(template.value())
            .orElseThrow(
                () -> new ClientException(String.format("Unknown Template %s", template.value())));
    Composition generate = (Composition) OPT_SKELETON_BUILDER.generate(operationalTemplate);

    new DtoToCompositionWalker()
        .walk(generate, findEntity( dto), templateProvider.buildIntrospect(template.value()).get());
    return NORMALIZER.normalize(generate);
  }


}
