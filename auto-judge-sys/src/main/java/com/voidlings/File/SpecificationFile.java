package com.voidlings;

public class SpecificationFile implements File{
    private String name;
    private String content; 

    public SpecificationFile (String name, String content) {
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