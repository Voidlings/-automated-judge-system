package com.voidlings.ReportHandling;

import com.voidlings.EvaluationHandling.AttributeEval;
import com.voidlings.EvaluationHandling.MethodEval;

import java.util.ArrayList;

/**
 * The PDFEvaluationReport class implements the EvaluationReport interface and represents
 * an evaluation report in PDF format. It includes information about attribute and method
 * evaluations, the number of tests passed, total tests, submission score, and maximum score.
 *
 * @author Voidlings
 * @version 1.0
 */
public class PDFEvaluationReport implements EvaluationReport {

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

    /**
     * Constructs a PDFEvaluationReport object with the given lists of attribute and method evaluations.
     *
     * @param attrEvals   The list of attribute evaluations.
     * @param methodEvals The list of method evaluations.
     */
    public PDFEvaluationReport(ArrayList<AttributeEval> attrEvals, ArrayList<MethodEval> methodEvals) {
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

    /**
     * Calculates the number of passed tests and the corresponding scores for attributes and methods.
     */
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

    /**
     * Generates a summary of the test cases, including the total tests, passed tests, failed tests,
     * submission score, and maximum score.
     *
     * @return A summary of the test cases.
     */
    public String getTestCaseSummary() {
        return String.format("\nSubmission Test and Grade Summary \nTotal Test Cases: %d\nPassed Test Cases: %d\nFailed Test Cases: %d\nGrade: %d / %d\n\n",
                totalTests, numTestsPassed, totalTests - numTestsPassed, submissionScore, maxScore);
    }

    /**
     * Generates detailed results for each test case, including the test case type (Attribute/Method),
     * grade, status (Passed/Failed), and comments.
     *
     * @return Detailed results for each test case.
     */
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
