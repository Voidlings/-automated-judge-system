package com.voidlings;
import java.util.ArrayList;
import java.util.List;

public class SubmissionFile {
    private String name;
    private String content;
    private List<String> attributeList; //made string for now
    private List<String> methodList; // made string for now

    // Constructor
    public SubmissionFile(String name, String content) {
        this.name = name;
        this.content = content;
        this.attributeList = new ArrayList<>();
        this.methodList = new ArrayList<>();
    }

    // Getter methods
    public String getName() {
        return name;
    }

    public List<String> extractAttributes() {
        String[] lines = content.split("\n");
        boolean inAttributeBlock = false;

        for (String line : lines) {
            if (line.contains("{")) {
                inAttributeBlock = true;
            } else if ((!line.contains("{") && line.contains("}")) || (line.contains("(") && line.contains(")"))) {
                break;
            } else if (inAttributeBlock && !line.trim().startsWith("//") && !line.trim().startsWith("/*")) {
                // Check for lines within the attribute block and not commented out
                attributeList.add(line); // Extract the attribute
            }
        }
        return attributeList;
    }

    public List<String> extractMethods() {
        methodList = new ArrayList<>();
        String[] lines = content.split("\n");
        boolean inMethodBlock = false;
        int openBrackets = 0;
        int closeBrackets = 0;
        StringBuilder currentMethod = new StringBuilder();

        for (String line : lines) {
            if ((line.contains("(") && line.contains(")") && line.contains("{")) &&
                (!line.trim().startsWith("//") && !line.trim().startsWith("/*"))) {
                // Identifying the start of a method
                openBrackets++;
                inMethodBlock = true;
                currentMethod.append(line).append("\n");
            }

            if (inMethodBlock) {
                if (line.contains("{")) {
                    openBrackets++;
                }
                if (line.contains("}")) {
                    closeBrackets++;
                }

                currentMethod.append(line).append("\n");

                if (openBrackets == closeBrackets) {
                    // End of the method block
                    methodList.add(currentMethod.toString());
                    currentMethod.setLength(0); // Clear StringBuilder
                    inMethodBlock = false;
                    openBrackets = 0;
                    closeBrackets = 0;
                }
            }
        }
        return methodList;
    }
}
