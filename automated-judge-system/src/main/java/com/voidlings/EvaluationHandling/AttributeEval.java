package com.voidlings.EvaluationHandling;

public class AttributeEval implements Eval {
    public String name; 
    public boolean passed; 
    public int score; 

    public AttributeEval(String name, boolean passed, int score){
        this.name = name; 
        this.passed = passed; 
        this.score = score; 
    }
}