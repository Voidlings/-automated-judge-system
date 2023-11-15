package com.voidlings.AssignmentHandling;

public class AssignmentAttribute{
  private String name;
  private String type;
  private String modifier;
  private String variableType;

  public AssignmentAttribute(String name, String type, String modifier, String variableType){
    this.name = name;
    this.type = type;
    this.modifier = modifier;
    this.variableType = variableType;
  }

  public String getName() {
    return this.name;
  }

  public String getType() {
    return this.type;
  }

  public String getModifier() {
    return this.modifier;
  }

  public String getVariableType() {
    return this.variableType;
  }

}