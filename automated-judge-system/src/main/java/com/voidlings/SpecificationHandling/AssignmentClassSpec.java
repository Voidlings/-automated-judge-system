package com.voidlings.SpecificationHandling;

import java.util.List;

import com.voidlings.AssignmentHandling.AssignmentAttribute;
import com.voidlings.AssignmentHandling.AssignmentClass;
import com.voidlings.AssignmentHandling.AssignmentMethod;

public class AssignmentClassSpec extends AssignmentClass{

  public AssignmentClassSpec(String name, List<AssignmentAttribute> attributes, List<AssignmentMethod> methods, String output) {
    super(name, attributes, methods, output);
  }

}