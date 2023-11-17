package com.voidlings.TestCases;

public class TestCase {
    private String name;
    private boolean passed;
    private double score;
    private String resultMessage;

    public TestCase(String name, boolean passed, double score, String resultMessage) {
        this.name = name;
        this.passed = passed;
        this.score = score;
        this.resultMessage = resultMessage;
    }

    public String getName() {
        return name;
    }

    public boolean isPassed() {
        return passed;
    }

    public double getScore() {
        return score;
    }

    public String getResultMessage() {
        return resultMessage;
    }
}
