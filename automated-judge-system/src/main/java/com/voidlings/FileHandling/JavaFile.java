package com.voidlings.FileHandling;

import java.util.ArrayList;
import java.util.List;

/**
 * The JavaFile class implements the FileComponent interface and represents a Java source file.
 * It includes methods to extract attributes and methods from the file content.
 *
 * @author Voidlings
 * @version 1.0
 */
public class JavaFile implements FileComponent {


    private String name;

    private String content;

    private String path;

    /**
     * List of extracted attribute lines from the file content.
     */
    private List<String> attributeList;

    /**
     * List of extracted method blocks from the file content.
     */
    private List<String> methodList;

    /**
     * Constructs a JavaFile object with the specified name, content, and path.
     *
     * @param name    The name of the Java file.
     * @param content The content of the Java file.
     * @param path    The path of the Java file in the file system.
     */
    public JavaFile(String name, String content, String path) {
        setName(name);
        this.content = content;
        this.attributeList = new ArrayList<>();
        this.methodList = new ArrayList<>();
        this.path = path;
    }

    /**
     * Retrieves the name of the Java file.
     *
     * @return The name of the Java file.
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Retrieves the content of the Java file.
     *
     * @return The content of the Java file.
     */
    public String getContent() {
        return this.content;
    }

    /**
     * Retrieves the path of the Java file in the file system.
     *
     * @return The path of the Java file.
     */
    @Override
    public String getPath() {
        return this.path;
    }

    /**
     * Extracts attributes from the Java file content.
     *
     * @return The list of extracted attribute lines.
     */
    public List<String> extractAttributes() {
        // ... (omitted for brevity)
        return attributeList;
    }

    /**
     * Extracts methods from the Java file content.
     *
     * @return The list of extracted method blocks.
     */
    public List<String> extractMethods() {
        // ... (omitted for brevity)
        return methodList;
    }

    /**
     * Sets the name of the Java file.
     *
     * @param name The new name of the Java file.
     */
    @Override
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Indicates whether the file component is a folder.
     *
     * @return false since JavaFile is not a folder.
     */
    @Override
    public Boolean isFolder() {
        return false;
    }
}
