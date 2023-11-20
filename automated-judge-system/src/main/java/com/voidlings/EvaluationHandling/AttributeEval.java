package com.voidlings.EvaluationHandling;

public class AttributeEval implements Eval {
    public String name; 
    public boolean passed; 
    public int score; 
    public int overallScore; 
    public String comment;

    public AttributeEval(String name, boolean passed, int score, int overallScore){
        this.name = name; 
        this.passed = passed; 
        this.score = score; 
        this.overallScore = overallScore;
        setComment();
    }

    public void setComment () {
        if (passed) {
            comment = "Attribute " + name + " exists.";
        } else {
            comment = "Attribute " + name + " is missing from the assignment or inaccurate declaration of attribute.";
        }
    }
}