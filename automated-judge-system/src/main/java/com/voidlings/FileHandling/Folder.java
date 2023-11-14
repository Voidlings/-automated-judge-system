package com.voidlings.FileHandling;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Folder implements FileComponent{
    private String name;
    private List<FileComponent> allComponents;
    private String path;

    // Constructor
    public Folder(String name, String path) {
        this.name = name;
        this.allComponents= new ArrayList<>();
        this.path= path;
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

    // Method to check if a file exists in the folder
    public Boolean fileExists(String name) {
        for (FileComponent file : allComponents) {
            System.out.println(file.getName());
            if (file.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public List<FileComponent> getFileComponents(){
        return this.allComponents;
    }

    // Getter method to retrieve the list of files
    public List<JavaFile> getJavaFiles() {
        List<JavaFile> javaFiles = new ArrayList<>();
        for (FileComponent component : allComponents) {
            if (!component.isFolder()) {
                javaFiles.add((JavaFile) component);
            }
        }
        return javaFiles;
    }

    // Getter method to retrieve the list of folders
    public List<Folder> getFolderList(){
        List<Folder> folders = new ArrayList<>();
        for (FileComponent component : allComponents) {
            if (component.isFolder()) {
                folders.add((Folder) component);
            }
        }
        return folders;
    }

    // Getter method to retrieve foldername
    public String getName(){
        return this.name;
    }

    public void addComponent(FileComponent component) {
        this.allComponents.add(component);
    }

    public FileComponent getComponentByName(String name) {
        for (FileComponent component : this.allComponents) {
            if (component.getName().equals(name)) {
                return component;
            }
        }
        return null;
    }

    public Boolean fileExistsRevised(String name){
        for (Folder folder : this.getFolderList()) {
            //System.out.println(folder.getName());
            for(JavaFile f: folder.getJavaFiles()){
                //System.out.println(folder.getName() + ": " + f.getName());
                if (f.getName().equals(name)){
                    System.out.println(name + " exists." + "\n");
                    return true;
                }
            }
        }
        //System.out.println(name + " not found.");
        return false;
    }

    @Override
    public void setName(String name) {
        this.name= name;
    }

    @Override
    public Boolean isFolder() {
        return true;
    }

    @Override
    public String getPath() {
        return this.path;
    }
}