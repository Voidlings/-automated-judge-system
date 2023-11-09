package com.voidlings;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Folder {
    private String name;
    private List<SubmissionFile> files;
    private List<Folder> folders;

    // Constructor
    public Folder(String name) {
        this.name = name;
        files = new ArrayList<>();
        folders= new ArrayList<>();
    }

    // Method to create the actual directory
    public File createFolder(String folderPath) {
        File directory = new File(folderPath);

        // Check if the directory doesn't exist
        if (!directory.exists()) {
            // Create the directory
            boolean success = directory.mkdirs();

            if (success) {
                System.out.println("Directory created successfully: " + folderPath);
                return directory;
            } else {
                System.err.println("Failed to create directory: " + folderPath);
                return null;
            }
        } else {
            return directory;
        }
    }

    // Method to add a file to the folder class
    public void addFile(SubmissionFile file) {
        files.add(file);
    }

    // Method to add a folder to the folder class
    public void addFolder(Folder folder) {
        folders.add(folder);
    }

    // Method to check if a file exists in the folder
    public Boolean fileExists(String name) {
        for (SubmissionFile file : files) {
            if (file.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    // Getter method to retrieve the list of files
    public List<SubmissionFile> getFiles() {
        return files;
    }

    // Getter method to retrieve the list of folders
    public List<Folder> getFolderList(){
        return this.folders;
    }

    // Getter method to retrieve foldername
    public String getName(){
        return this.name;
    }

    public Folder getSubFolderByName(String folderName){
        for (Folder folder : this.folders) {
            if (folder.getName().equals(folderName)) {
                return folder;
            }
        }
        return null;
    }
}