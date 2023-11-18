package com.voidlings.TestCases;

import java.util.ArrayList;

import com.voidlings.SpecificationHandling.SpecificationComponents;

public class MethodTestCase{
    public int calculateMethodScore(ArrayList<SpecificationComponents> specs, ArrayList<SpecificationComponents> javaFiles){
      int score = 0;
      int totalScore = 0;
      int counter = 0;
      int foundMethods = 0;
      boolean classFound = false;
      boolean methodFound = false;
      // Takes ArrayList of all specification information as well as ArrayList of all java classes.
      for (SpecificationComponents specClass : specs){
        // Find if the class exists in the assignment specs, and then continue grading logic.
        for (SpecificationComponents java: javaFiles){
          if (java.getClassName().contains(specClass.getClassName())){
            // System.out.println("Class " + java.getClassName() + " exists.");
            classFound = true;
            // For each method in methods, if method exists, then mark is given.
            for (String methodSpec : specClass.getAllMethods()){
              for (String assignMethod : java.getAllMethods()){
                if (methodSpec.replace(" ","").contains(assignMethod.replace(" ",""))){
                  methodFound = true;
                  foundMethods++;
                  // System.out.println("Method " + methodSpec + " exists.");

                  // Marks added to score.
                  int marks = Integer.valueOf(specClass.getAllMarks().get(counter).replace(" ",""));
                  score = marks + score;
                  totalScore = totalScore + marks;
                }
              }
              if (methodFound == false){
                System.out.println("Method " + methodSpec + " is missing from the assignment.");
              }

              methodFound = false; // Reset before loop restarts.
              counter++; // Update counter - index for finding correct mark.
            }
          }
          counter = 0;
        }
        // If class not found, then no grades given, a message stating such is added to the PDF.
        if (classFound == false){
          System.out.println("Class " + specClass.getClassName() + " is not present in the code. No marks given.");
        }

        // Reset before loop restarts.
        classFound = false;
        System.out.println("Methods Found: " + foundMethods + "/" + specClass.numMethods() + "\nMethod marks obtained from class " + specClass.getClassName() + ": " + score + "\n");
        foundMethods = 0;
        score = 0;
      }
      System.out.println("Total score for methods obtained: " + totalScore + "\n");
      return totalScore;
    }
}
