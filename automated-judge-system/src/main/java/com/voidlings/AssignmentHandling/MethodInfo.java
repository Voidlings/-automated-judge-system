package com.voidlings.AssignmentHandling;

import java.util.List;

public class MethodInfo {
    private String methodName;
    private String returnType;
    private List<ParameterInfo> parameters;

    public MethodInfo(String methodName, String returnType, List<ParameterInfo> parameters) {
      this.methodName = methodName;
      this.returnType = returnType;
      this.parameters = parameters;
    }

    public String getMethodName() {
      return methodName;
    }

    public String getReturnType() {
      return returnType;
    }

    public List<ParameterInfo> getParameters() {
      return parameters;
    }
  }