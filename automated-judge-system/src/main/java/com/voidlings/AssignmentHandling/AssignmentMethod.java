package com.voidlings.AssignmentHandling;
import java.util.Objects;

public class AssignmentMethod{
  private String signature;
  private String type;
  private String methodString;
  private String output;

  public AssignmentMethod(String signature, String type, String methodString, String output) {
    this.signature = signature;
    this.type = type;
    this.methodString = methodString;
    this.output = output;
  }

  public String getSignature() {
    return this.signature;
  }

  public String getType() {
    return this.type;
  }

  public String getMethodString() {
    return this.methodString;
  }

  public String extractOutput() {
    return this.output;
  }
  
}