package com.voidlings.TestCases;

import java.util.List;

import com.voidlings.AssignmentHandling.AssignmentAttribute;
import com.voidlings.AssignmentHandling.AssignmentMethod;

public class ClassTestCase implements TestCase{
  private String expectedName;
  private List<AssignmentAttribute> expectedAttr;
  private List<AssignmentMethod> expectedMethods;


  public String getExpectedName() {
    return this.expectedName;
  }

  public List<AssignmentAttribute> getExpectedAttr() {
    return this.expectedAttr;
  }

  public List<AssignmentMethod> getExpectedMethods() {
    return this.expectedMethods;
  }

  public Boolean checkClassName(String name){

    return false;
  }

  public int compareAttributes(){
    
    return 0;
  }

  public int compareMethods(){

    return 0;
  }

  public void executeTest(){

  }
  
}
