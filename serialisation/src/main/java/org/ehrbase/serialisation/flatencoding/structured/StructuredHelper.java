/*
 *  Copyright (c) 2021  Stefan Spiska (Vitasystems GmbH) and Hannover Medical School
 *
 *  This file is part of Project EHRbase
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 */

package org.ehrbase.serialisation.flatencoding.structured;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.ValueNode;
import org.apache.commons.lang3.StringUtils;
import org.ehrbase.serialisation.jsonencoding.JacksonUtil;
import org.ehrbase.util.exception.SdkException;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Collectors;

import static org.ehrbase.webtemplate.parser.OPTParser.PATH_DIVIDER;

public class StructuredHelper {
  private static final ObjectMapper OBJECT_MAPPER = JacksonUtil.getObjectMapper();

  private StructuredHelper() {
    // NOP
  }

  public static String convert(String structuredString) {
    JsonNode jsonNode = null;
    try {

      jsonNode = OBJECT_MAPPER.readTree(structuredString);
        Map<String, JsonNode> convert = convert("", jsonNode);

        return OBJECT_MAPPER.writeValueAsString(convert.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().asText())));
    } catch (JsonProcessingException e) {

     throw  new SdkException(e.getMessage(),e);
    }

  }



  private static Map<String, JsonNode> convert(String path, JsonNode jsonNode) {
    if (jsonNode instanceof ValueNode) {

      return Map.of(path, jsonNode);
    } else if (jsonNode instanceof ObjectNode) {
      Map<String, JsonNode> map = new HashMap<>();
      for (Iterator<Map.Entry<String, JsonNode>> it = jsonNode.fields(); it.hasNext(); ) {
        Map.Entry<String, JsonNode> field = it.next();
         final String newPath ;
          if(StringUtils.startsWith(field.getKey(),"|") || StringUtils.isBlank(path)){
               newPath = path + field.getKey();
          }else {
              newPath = path +PATH_DIVIDER + field.getKey();
          }
          map.putAll(convert(newPath  , field.getValue()));
      }
      return map;
    } else if (jsonNode instanceof ArrayNode) {
        Map<String, JsonNode> map = new HashMap<>();

      for (int i = 0; i < jsonNode.size(); i++){
          JsonNode child = jsonNode.get(i);
          final String newPath ;
          if (i >0 ){
               newPath = path + ":" + i;
          }else{
              newPath = path;
          }
          map.putAll(convert(newPath,child));
      }

        return map;
    }

    throw new SdkException(
        String.format("Unknown Structure %s", jsonNode.getClass().getSimpleName()));
  }
}
