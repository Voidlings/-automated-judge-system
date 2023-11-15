package com.voidlings.AssignmentHandling;

public class ParameterInfo {
      private String paramName;
      private String paramType;

      public ParameterInfo(String paramName, String paramType) {
          this.paramName = paramName;
          this.paramType = paramType;
      }

      public String getParamName() {
          return paramName;
      }

      public String getParamType() {
          return paramType;
      }
    }