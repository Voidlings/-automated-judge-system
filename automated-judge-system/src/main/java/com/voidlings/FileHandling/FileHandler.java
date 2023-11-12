package com.voidlings;

public interface FileHandler{
    public abstract Boolean checkFormat(String filePath);
    public abstract Boolean containsFileType(String filePath);
    public abstract Folder extractFiles(String filePath, String destinationPath);
    public abstract Folder getFolder();

}