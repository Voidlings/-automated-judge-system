package com.voidlings.ReportHandling;

import com.voidlings.EvaluationHandling.AttributeEval;
import com.voidlings.EvaluationHandling.MethodEval;

import java.util.ArrayList;

public class PDFEvaluationReport implements EvaluationReport{
    private ArrayList<AttributeEval> attrEvals;
    private ArrayList<MethodEval> methodEvals; 
    private int numTestsPassed;
    private int totalTests;
    private int submissionScore;
    private int maxScore;
    private int attributeScore; 
    private int methodScore;
    private int maxAttributeScore;
    private int maxMethodScore;

    public PDFEvaluationReport(ArrayList<AttributeEval> attrEvals, ArrayList<MethodEval>  methodEvals) {
        this.attrEvals = attrEvals;
        this.methodEvals = methodEvals;
        totalTests = attrEvals.size() + methodEvals.size();
        numTestsPassed = 0;
        attributeScore = 0;
        methodScore = 0;
        maxAttributeScore = 0;
        maxMethodScore = 0;
        passedTests();
        maxScore = maxAttributeScore + maxMethodScore;
        submissionScore = attributeScore + methodScore;
    }

    public void passedTests() {
        for (AttributeEval attrEval : attrEvals) {
            maxAttributeScore += attrEval.overallScore;
            
            if (attrEval.passed) {
                numTestsPassed++;
                attributeScore += attrEval.score;
            }
        }
        for (MethodEval methodEval : methodEvals) {
            maxMethodScore += methodEval.overallScore;

            if (methodEval.passed) {
                numTestsPassed++;
                methodScore += methodEval.score;
            }
        }
    }

    public String getTestCaseSummary() {
        return String.format("\nSubmission Test and Grade Summary \nTotal Test Cases: %d\nPassed Test Cases: %d\nFailed Test Cases: %d\nGrade: %d / %d\n\n",
                             totalTests, numTestsPassed, totalTests - numTestsPassed, submissionScore, maxScore);
    }
    
    public String getTestCaseResults() {
        StringBuilder results = new StringBuilder();
        
        results.append("\nTest Case Results \n");

        for (AttributeEval attributeEval : attrEvals) {
            results.append(String.format("\nAttribute Test Case: %s\nGrade: %s / %s \nStatus: %s \nComment: %s \n",
                                         attributeEval.name, attributeEval.score, attributeEval.overallScore,
                                         attributeEval.passed ? "Passed" : "Failed", attributeEval.comment));
        }
    
        for (MethodEval methodEval : methodEvals) {
            results.append(String.format("\nMethod Test Case: %s\nGrade: %s / %s \nStatus: %s \nComment: %s \n",
                                         methodEval.name, methodEval.score, methodEval.overallScore, 
                                         methodEval.passed ? "Passed" : "Failed", methodEval.comment));
        }
    
        return results.toString();
    }
}
