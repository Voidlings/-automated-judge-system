package com.voidlings.EvaluationHandling;

public class MethodEval implements Eval{
    public String name;
    public boolean passed;
    public int score;
    public int overallScore;
    public String comment; 

    public MethodEval(String name, boolean passed, int score, int overallScore){
        this.name = name;
        this.passed = passed;
        this.score = score;
        this.overallScore = overallScore;
        setComment();
    }

    public void setComment () {
        if (passed) {
            comment = "Method " + name + " exists.";
        } else {
            comment = "Method " + name + " is missing from the assignment or inaccurate declaration of method.";
        }
    }
}
