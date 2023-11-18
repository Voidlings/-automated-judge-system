package com.voidlings.TestCases;

import java.util.ArrayList;

import com.voidlings.SpecificationHandling.SpecificationComponents;

public class AttributeTestCase {
  public int calculateAttributeScore(ArrayList<SpecificationComponents> specs, ArrayList<SpecificationComponents> javaFiles){
    int score = 0;
    int totalScore = 0;
    int numFound = 0;
    boolean classFound = false;
    boolean attrFound = false;

    for (SpecificationComponents specClass : specs){
      // Find if the class exists in the assignment specs, and then continue grading logic.
      for (SpecificationComponents java: javaFiles){
        if (java.getClassName().contains(specClass.getClassName())){
          // System.out.println("Class " + java.getClassName() + " exists.");
          classFound = true;
          // For each attribute in attributes, if attribute exists, then mark is given. 1 mark per attribute.
          for (String attrSpec : specClass.getAllAttributes()){
            for (String assignAttr : java.getAllAttributes()){
              if (attrSpec.replace(" ","").contains(assignAttr.replace(" ","").replace("private", "").replace("public", "").replace("static", ""))){
                attrFound = true;
                numFound++;
                totalScore++;
              }
            }
            if (attrFound == false){
              System.out.println("Attribute " + attrSpec + " is missing from the assignment.");
            }
            attrFound = false; // Reset before loop restarts.
          }
        }
      }
      // If class not found, then no grades given, a message stating such is added to the PDF.
      if (classFound == false){
        System.out.println("Class " + specClass.getClassName() + " is not present in the code. No marks given.");
      }
      System.out.println("Attributes found: " + numFound + "/" + specClass.numAttributes() + "\nAttribute marks obtained from class " + specClass.getClassName() + ": " + numFound + "\n");
      numFound = 0;
      classFound = false;
    }

    System.out.println("Total score for attributes obtained: " + totalScore + "\n");
    return totalScore;
  }
}
