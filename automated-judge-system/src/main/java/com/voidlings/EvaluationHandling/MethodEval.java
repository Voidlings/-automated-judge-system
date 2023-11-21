package com.voidlings.EvaluationHandling;

/**
 * The MethodEval class implements the Eval interface and represents the evaluation of a method.
 * It includes information such as the method name, evaluation result, score, overall score, and a comment.
 * The comment is automatically generated based on the evaluation result.
 *
 * @author Voidlings
 * @version 1.0
 */
public class MethodEval implements Eval {


    public String name;

 
    public boolean passed;


    public int score;


    public int overallScore;

    /**
     * The automatically generated comment based on the evaluation result.
     */
    public String comment;

    /**
     * Constructs a MethodEval object with the specified method name, evaluation result, score, and overall score.
     * The comment is set automatically based on the evaluation result.
     *
     * @param name         The name of the method.
     * @param passed       Indicates whether the method evaluation passed or not.
     * @param score        The score obtained for the method evaluation.
     * @param overallScore The overall score for the method.
     */
    public MethodEval(String name, boolean passed, int score, int overallScore) {
        this.name = name;
        this.passed = passed;
        this.score = score;
        this.overallScore = overallScore;
        setComment();
    }

    /**
     * Sets the comment based on the evaluation result.
     * If the method evaluation passed, the comment indicates that the method exists.
     * If the method evaluation failed, the comment indicates that the method is missing
     * from the assignment or has an inaccurate declaration.
     */
    public void setComment() {
        if (passed) {
            comment = "Method exists.";
        } else {
            comment = "Method is missing from the assignment or has an inaccurate declaration of method.";
        }
    }
}
