package com.voidlings.TestCases;

import java.util.ArrayList;

import com.voidlings.SpecificationHandling.SpecificationComponents;
import com.voidlings.EvaluationHandling.AttributeEval;

public class AttributeTestCase implements TestCase{
  private ArrayList<SpecificationComponents> specs;
  private ArrayList<SpecificationComponents> javaFiles;
  private int totalScore; 
  private ArrayList<AttributeEval> attributeEvals;

  public AttributeTestCase(ArrayList<SpecificationComponents> specs, ArrayList<SpecificationComponents> javaFiles){
    this.specs = specs;
    this.javaFiles = javaFiles;
    this.totalScore = 0;
    this.attributeEvals = new ArrayList<AttributeEval>();
    performTests();
  }

  public void performTests() {
    // Takes ArrayList of all specification information as well as ArrayList of all java classes.
    for (SpecificationComponents specClass : specs){
        // Find if the class exists in the assignment specs, and then continue grading logic.
        for (SpecificationComponents java: javaFiles){
            if (java.getClassName().contains(specClass.getClassName())){
                // For each attribute in specClass, check if it exists in java.
                for (String specAttr : specClass.getAllAttributes()) {
                  final String finalSpecAttr = specAttr.replace(" ", "").replace("private", "").replace("public", "").replace("static", "");

                  boolean attrExists = java.getAllAttributes().stream()
                      .map(attr -> attr.replace(" ", "").replace("private", "").replace("public", "").replace("static", ""))
                      .anyMatch(attr -> attr.contains(finalSpecAttr));

                  if (attrExists) {
                    attributeEvals.add(new AttributeEval(specAttr, true, 1, 1));
                    totalScore += 1;
                  } else {
                    attributeEvals.add(new AttributeEval(specAttr, false, 0, 1));
                  }
                }
            }
        }
    }
}

  public int getTotalScore(){
    return totalScore;
  }

  public ArrayList<AttributeEval> getAttributeEvals(){
    return attributeEvals;
  }

  /* 
  public int calculateAttributeScore(){
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
  } */
}
