package com.voidlings.FileHandling;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * The Folder class implements the FileComponent interface and represents a folder in the file system.
 * It contains methods to create a directory, check if a file exists in the folder, retrieve file components,
 * retrieve Java files, retrieve folders, add a component to the folder, get a component by name, retrieve Java file paths,
 * and provide information about the folder.
 *
 * @author Voidlings
 * @version 1.0
 */

public class Folder implements FileComponent {


    private String name;

    /**
     * List of file components (files and sub-folders) contained in the folder.
     */
    private List<FileComponent> allComponents;

    /**
     * The path of the folder in the file system.
     */
    private String path;

    /**
     * Constructs a Folder object with the specified name and path.
     *
     * @param name The name of the folder.
     * @param path The path of the folder in the file system.
     */
    public Folder(String name, String path) {
        this.name = name;
        this.allComponents = new ArrayList<>();
        this.path = path;
    }

    /**
     * Creates the actual directory in the file system.
     *
     * @param folderPath The path of the directory to be created.
     * @return The created directory as a File object, or null if the directory creation fails.
     */
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

    /**
     * Checks if a file with the specified name exists in the folder.
     *
     * @param name The name of the file to check.
     * @return true if the file exists, false otherwise.
     */
    public Boolean fileExists(String name) {
        for (FileComponent file : allComponents) {
            if (file.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Retrieves the list of file components (files and sub-folders) contained in the folder.
     *
     * @return The list of file components.
     */
    public List<FileComponent> getFileComponents() {
        return this.allComponents;
    }

    /**
     * Retrieves the list of Java files contained in the folder.
     *
     * @return The list of Java files.
     */
    public List<JavaFile> getJavaFiles() {
        List<JavaFile> javaFiles = new ArrayList<>();
        for (FileComponent component : allComponents) {
            if (!component.isFolder()) {
                javaFiles.add((JavaFile) component);
            }
        }
        return javaFiles;
    }

    /**
     * Retrieves the list of sub-folders contained in the folder.
     *
     * @return The list of sub-folders.
     */
    public List<Folder> getFolderList() {
        List<Folder> folders = new ArrayList<>();
        for (FileComponent component : allComponents) {
            if (component.isFolder()) {
                folders.add((Folder) component);
            }
        }
        return folders;
    }

    /**
     * Retrieves the name of the folder.
     *
     * @return The name of the folder.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Adds a file component (file or sub-folder) to the folder.
     *
     * @param component The file component to be added.
     */
    public void addComponent(FileComponent component) {
        this.allComponents.add(component);
    }

    /**
     * Retrieves a file component (file or sub-folder) by its name.
     *
     * @param name The name of the file component.
     * @return The file component with the specified name, or null if not found.
     */
    public FileComponent getComponentByName(String name) {
        for (FileComponent component : this.allComponents) {
            if (component.getName().equals(name)) {
                return component;
            }
        }
        return null;
    }

    /**
     * Retrieves the list of Java file paths contained in the folder.
     *
     * @return The list of Java file paths.
     */
    public List<String> getJavaFilePaths() {
        List<String> javaNames = new ArrayList<>();
        for (FileComponent component : allComponents) {
            if (!component.isFolder()) {
                javaNames.add(component.getPath());
            }
        }
        return javaNames;
    }

    /**
     * Sets the name of the folder.
     *
     * @param name The new name of the folder.
     */
    @Override
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Indicates whether the file component is a folder.
     *
     * @return true if the file component is a folder, false otherwise.
     */
    @Override
    public Boolean isFolder() {
        return true;
    }

    /**
     * Retrieves the path of the folder in the file system.
     *
     * @return The path of the folder.
     */
    @Override
    public String getPath() {
        return this.path;
    }
}
