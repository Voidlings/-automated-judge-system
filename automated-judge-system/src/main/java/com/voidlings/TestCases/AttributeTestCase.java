package com.voidlings.TestCases;

import java.util.List;

public class AttributeTestCase implements TestCase{
    // Assuming this class is called to test for attributes.
    // Collects a list of the attributes from the assignment + the spec
    public int attributeGrading(List<String> assignmentAttributes, List <String> assignmentAttributeSpec){
      int score = 0;

      // Checks through assignment attribute list for each attribute. Presence of 1 gives 1 mark.
      for (String s1: assignmentAttributeSpec){
        for (String s2: assignmentAttributes){
          if (s1.equals(s2)){
            score++;
          }
        }
      }

      return score;
    }
}
