package com.voidlings.ReportHandling;

import com.voidlings.TestCases.TestCase;

import java.util.List;
import java.util.stream.Collectors;

public class EvaluationReport {
    private List<TestCase> testCases;
    private int numTestsPassed;
    private int totalTests;
    private double submissionScore;
    private String reportHeader;

    public EvaluationReport(List<TestCase> testCases) {
        this.testCases = testCases;
        this.numTestsPassed = (int) testCases.stream().filter(TestCase::isPassed).count();
        this.totalTests = testCases.size();
        this.submissionScore = calculateTotalSubmissionScore();
    }

    private double calculateTotalSubmissionScore() {
        double score = 0;
        for (TestCase testCase : testCases) {
            if (testCase.isPassed()) {
                score += testCase.getScore();
            }
        }
        return score;
    }

    public List<TestCase> getPassedCases() {
        return testCases.stream().filter(TestCase::isPassed).collect(Collectors.toList());
    }

    public List<TestCase> getFailedCases() {
        return testCases.stream().filter(testCase -> !testCase.isPassed()).collect(Collectors.toList());
    }

    public double getSubmissionScore() {
        return submissionScore;
    }

    public String getReportHeader() {
        // Implement this method based on your assignment and course information
        return "Test header goes here";
    }

    public String getTestCaseSummary() {
        return String.format("Total Number of Test Cases: %d. Number of Passed Test Cases: %d. Number of Failed Test Cases: %d",
                totalTests, numTestsPassed, totalTests - numTestsPassed);
    }
    
    public String getTestCaseResults() {
        StringBuilder results = new StringBuilder();
        int count = 0;
        for (TestCase testCase : testCases) {
            results.append(String.format("Name: %s. Status: %s. Marks Earned: %.2f. Feedback: %s. ",
                    testCase.getName(), testCase.isPassed() ? "Passed" : "Failed", testCase.getScore(), testCase.getResultMessage()));
            count++;
            if (count % 3 == 0) {
                results.append("\n");
            }
        }
        return results.toString();
    }
}
