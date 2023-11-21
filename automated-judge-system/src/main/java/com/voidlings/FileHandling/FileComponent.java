package com.voidlings.FileHandling;

public interface FileComponent {
    public abstract String getName();
    public abstract void setName(String name);
    public abstract Boolean isFolder();
    public abstract String getPath();
}
