package com.voidlings.TestCases;

import java.util.List;

public class MethodTestCase implements TestCase{
  // TODO: Need to be passed parameterInfo as well.
    public int methodGrading(List<String> assignmentMethods, List <String> assignmentMethodSpec){
      int score = 0;

      // Checks through assignment methods for presence of methods. If present, one mark is awarded. Checks through parameters, if these are correct.
      for (String s1: assignmentMethodSpec){
        // Checks if method name is present. Awarded 5 marks.
        for (String s2: assignmentMethods){
          if (s1.equals(s2)){
            score = score + 5;

            // If method has all the necessary parameters, 1 mark awarded.

          }
        }
      }

      return score;
    }
}
