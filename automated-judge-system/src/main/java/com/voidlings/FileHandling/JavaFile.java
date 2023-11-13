package com.voidlings.FileHandling;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.FileNotFoundException;

public class JavaFile implements FileComponent{
    private String name;
    private String content;
    private String path;
    private List<String> attributeList; //made string for now
    private List<String> methodList; // made string for now

    // Constructor
    public JavaFile(String name, String content, String path) {
        setName(name);
        this.content = content;
        this.attributeList = new ArrayList<>();
        this.methodList = new ArrayList<>();
        this.path = path;
    }

    // Getter methods
    @Override
    public String getName() {
        return name;
    }

    public String getContent(){
        return this.content;
    }

    @Override
    public String getPath(){
        return this.path;
    }

    public List<String> extractAttributes(){
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

    @Override
    public void setName(String name) {
        this.name= name;
    }

    @Override
    public Boolean isFolder() {
        return false;
    }
}
