package com.voidlings.AssignmentHandling;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JavaClassInfo {
  private String className;
  private List<String> attributeNames = new ArrayList<>();
  private Map<String, MethodInfo> methodInfoMap = new HashMap<>();

  public JavaClassInfo(String className) {
      this.className = className;
  }

  public String getClassName() {
      return className;
  }

  public List<String> getAttributeNames() {
      return attributeNames;
  }

  public Map<String, MethodInfo> getMethodInfoMap() {
      return methodInfoMap;
  }

  public void addAttributeName(String attributeName) {
      this.attributeNames.add(attributeName);
  }

  public void addMethodName(String methodName, String returnType, List<ParameterInfo> parameters) {
      MethodInfo methodInfo = new MethodInfo(methodName, returnType, parameters);
      this.methodInfoMap.put(methodName, methodInfo);
  }
}
