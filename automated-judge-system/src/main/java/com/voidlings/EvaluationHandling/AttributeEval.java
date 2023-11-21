package com.voidlings.EvaluationHandling;

/**
 * The AttributeEval class implements the Eval interface and represents the evaluation of an attribute.
 * It includes information such as the attribute name, evaluation result, score, overall score, and a comment.
 * The comment is automatically generated based on the evaluation result.
 *
 * @author Voidlings
 * @version 1.0
 */
public class AttributeEval implements Eval {

    /**
     * The name of the attribute being evaluated.
     */
    public String name;

    /**
     * Indicates whether the attribute evaluation passed or not.
     */
    public boolean passed;

    /**
     * The score obtained for the attribute evaluation.
     */
    public int score;

    /**
     * The overall score for the attribute, which may include multiple evaluations.
     */
    public int overallScore;

    /**
     * The automatically generated comment based on the evaluation result.
     */
    public String comment;

    /**
     * Constructs an AttributeEval object with the specified attribute name, evaluation result, score, and overall score.
     * The comment is set automatically based on the evaluation result.
     *
     * @param name         The name of the attribute.
     * @param passed       Indicates whether the attribute evaluation passed or not.
     * @param score        The score obtained for the attribute evaluation.
     * @param overallScore The overall score for the attribute.
     */
    public AttributeEval(String name, boolean passed, int score, int overallScore) {
        this.name = name;
        this.passed = passed;
        this.score = score;
        this.overallScore = overallScore;
        setComment();
    }

    /**
     * Sets the comment based on the evaluation result.
     * If the attribute evaluation passed, the comment indicates that the attribute exists.
     * If the attribute evaluation failed, the comment indicates that the attribute is missing
     * from the assignment or has an inaccurate declaration.
     */
    public void setComment() {
        if (passed) {
            comment = "Attribute " + name + " exists.";
        } else {
            comment = "Attribute " + name + " is missing from the assignment or has an inaccurate declaration of attribute.";
        }
    }
}
