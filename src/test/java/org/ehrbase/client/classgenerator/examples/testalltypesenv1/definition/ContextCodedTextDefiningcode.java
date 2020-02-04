package org.ehrbase.client.classgenerator.examples.testalltypesenv1.definition;

import org.ehrbase.client.classgenerator.EnumValueSet;

public enum ContextCodedTextDefiningcode implements EnumValueSet {
  VALUE2("value2", "*", "local", "at0007"),

  VALUE3("value3", "*", "local", "at0008"),

  VALUE1("value1", "*", "local", "at0006");

  private String value;

  private String description;

  private String terminologyId;

  private String code;

  ContextCodedTextDefiningcode(String value, String description, String terminologyId,
                               String code) {
    this.value = value;
    this.description = description;
    this.terminologyId = terminologyId;
    this.code = code;
  }

  public String getValue() {
    return this.value;
  }

  public String getDescription() {
    return this.description;
  }

  public String getTerminologyId() {
    return this.terminologyId;
  }

  public String getCode() {
    return this.code;
  }
}
