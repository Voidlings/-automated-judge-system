package com.voidlings;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Folder {
    private List<File> files;

    // Constructor
    public Folder() {
        files = new ArrayList<>();
    }

    // Method to create the actual folder
    public void createFolder(String folderPath) {
        File folder = new File(folderPath);
        if (!folder.exists()) {
            folder.mkdirs(); // Create the directory if it doesn't exist
        }
    }

    // Method to add a file to the folder
    public void addFile(File file) {
        files.add(file);
    }

    // Method to check if a file exists in the folder
    public boolean fileExists(String name) {
        for (File file : files) {
            if (file.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    // Getter method to retrieve the list of files
    public List<File> getFiles() {
        return files;
    }
}