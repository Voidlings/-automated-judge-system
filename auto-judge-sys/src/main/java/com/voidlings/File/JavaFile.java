package com.voidlings;

public class JavaFile implements File{
    private String name;
    private String content; 

    public JavaFIle (String name, String content) {
        this.name = name; 
        this.content = content;
    }

    public String getContent() {
        return content; 
    }

    public String getName() {
        return name;
    }
}