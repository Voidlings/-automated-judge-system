package com.voidlings.AssignmentHandling;

import java.util.List;

public class AssignmentClass{
  private String name;
  private List<AssignmentAttribute> attributes;
  private List<AssignmentMethod> methods;
  private String output;

  public AssignmentClass(String name, List<AssignmentAttribute> attributes, List<AssignmentMethod> methods, String output) {
    this.name = name;
    this.attributes = attributes;
    this.methods = methods;
    this.output = output;
  }

  public String getName() {
    return this.name;
  }

  public List<AssignmentAttribute> getAttributes() {
    return this.attributes;
  }

  public List<AssignmentMethod> getMethods() {
    return this.methods;
  }

  public String extractOutput() {
    return this.output;
  }

}