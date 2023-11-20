package com.voidlings.TestCases;

import java.util.ArrayList;

import org.w3c.dom.Attr;

import com.voidlings.SpecificationHandling.SpecificationComponents;
import com.voidlings.EvaluationHandling.AttributeEval;

public class AttributeTestCase {
  private ArrayList<SpecificationComponents> specs;
  private ArrayList<SpecificationComponents> javaFiles;
  private int totalScore; 
  private ArrayList<AttributeEval> attributeEvals;

  public AttributeTestCase(ArrayList<SpecificationComponents> specs, ArrayList<SpecificationComponents> javaFiles){
    this.specs = specs;
    this.javaFiles = javaFiles;
    this.totalScore = 0;
    this.attributeEvals = new ArrayList<AttributeEval>();
    attrTestcase();
  }

  public void attrTestcase () {
    for (SpecificationComponents specClass : specs){
      // Find if the class exists in the assignment specs, and then continue grading logic.
      for (SpecificationComponents java: javaFiles){
        if (java.getClassName().contains(specClass.getClassName())){
          // System.out.println("Class " + java.getClassName() + " exists.");

          // For each attribute in attributes, if attribute exists, then mark is given. 1 mark per attribute.
          for (String assignAttr : java.getAllAttributes()){
            if (assignAttr.replace(" ","").contains(assignAttr.replace(" ","").replace("private", "").replace("public", "").replace("static", ""))){
              attributeEvals.add(new AttributeEval(assignAttr, true, 1, 1));
              totalScore += 1;
              ////System.out.println("Attribute " + assignAttr + " is implemented in the assignment.");
            } else {
              attributeEvals.add(new AttributeEval(assignAttr, false, 0, 1));
              //System.out.println("Attribute " + assignAttr + " is missing from the assignment.");
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
