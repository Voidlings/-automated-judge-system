package com.voidlings.FileHandling;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * The ZIPFileHandler class implements the FileHandler interface for handling ZIP files.
 * It provides methods to check the format, extract files, and check if the ZIP contains Java files.
 * Additionally, it creates Folder and JavaFile instances for the extracted files.
 *
 * @author Voidlings
 * @version 1.0
 */
public class ZIPFileHandler implements FileHandler {

    /**
     * The Folder instance to store the extracted files and folders.
     */
    private Folder submissionFolder;

    /**
     * Constructs a ZIPFileHandler object with the specified folder name and path.
     *
     * @param folderName The name of the folder.
     * @param path       The path of the folder.
     */
    public ZIPFileHandler(String folderName, String path) {
        submissionFolder = new Folder(folderName, path);
    }

    /**
     * Checks if the provided file path has a ZIP format.
     *
     * @param path The file path to check.
     * @return true if the file has a ZIP format, false otherwise.
     */
    @Override
    public Boolean checkFormat(String path) {
        // Check if the file exists
        File file = new File(path);
        if (!file.exists() || !file.isFile()) {
            return false;
        }
        
        // Checks if the format of the file is zip
        path = path.toLowerCase();
        return path.endsWith(".zip");
    }

    /**
     * Checks if the ZIP file contains Java files.
     *
     * @param filePath The path to the ZIP file.
     * @return true if the ZIP file contains Java files, false otherwise.
     */
    @Override
    public Boolean containsFileType(String filePath) {
        // Checks if the zip contains java files
        try (ZipInputStream zipIn = new ZipInputStream(new FileInputStream(filePath))) {
            ZipEntry entry;
            while ((entry = zipIn.getNextEntry()) != null) {
                if (!entry.isDirectory() && entry.getName().endsWith(".java")) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Extracts files from the ZIP file and creates Folder and JavaFile instances for the extracted files.
     *
     * @param zipFilePath      The path to the ZIP file.
     * @param destinationPath  The destination path to extract the files.
     * @return The Folder instance containing the extracted files and folders, or null if extraction fails.
     */
    @Override
    public Folder extractFiles(String zipFilePath, String destinationPath) {
        if (!containsFileType(zipFilePath)) {
            return null;
        }

        File outputFolder = submissionFolder.createFolder(destinationPath);

        if (outputFolder == null) {
            System.err.println("Failed to create the destination directory.");
            return null;
        }

        // Extract all the files in the zip
        try (ZipInputStream zipIn = new ZipInputStream(new FileInputStream(zipFilePath))) {
            ZipEntry entry;
            while ((entry = zipIn.getNextEntry()) != null) {
                String entryName = entry.getName();
                File outputFile = new File(destinationPath, entryName);

                if (entry.isDirectory()) {
                    // Skip directories
                    System.out.println("Directory Encountered: " + entryName);
                    continue;
                }

                if (!outputFile.getParentFile().exists()) {
                    // Ensure directories are made for the files
                    outputFile.getParentFile().mkdirs();
                }

                // Use array buffer to read entry
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = zipIn.read(buffer)) != -1) {
                    bos.write(buffer, 0, bytesRead);
                }

                if (!entryName.endsWith(".java")) {
                    // If a non-Java file is encountered, only add it to the destination directory
                    try (FileOutputStream fos = new FileOutputStream(outputFile)) {
                        fos.write(bos.toByteArray());
                    }
                    continue;
                }

                // If a Java file is encountered, add to the destination directory and create JavaFile instances
                String content = new String(bos.toByteArray()); // Content for JavaFile

                if (entryName.contains("/")) {
                    // If the entry is in a subdirectory, create nested folders
                    String[] folders = entryName.split("/");
                    Folder currentFolder = this.submissionFolder;

                    for (int i = 0; i < folders.length - 1; i++) {
                        // Check if the folder already exists
                        FileComponent existingComponent = currentFolder.getComponentByName(folders[i]);
                        if (existingComponent == null) {
                            currentFolder.addComponent(new Folder(folders[i], (destinationPath + "/" + folders[i])));
                            existingComponent = currentFolder.getComponentByName(folders[i]);
                        }
                        currentFolder = (Folder) existingComponent;
                    }
                    currentFolder.addComponent(new JavaFile(folders[folders.length - 1], content, (destinationPath + "/" + entryName)));

                    // Write the content to the outputFile
                    try (FileOutputStream fos = new FileOutputStream(outputFile)) {
                        fos.write(("package com.voidlings.Submissions." + currentFolder.getName() + ";\n")
                                .getBytes());
                        fos.write(bos.toByteArray());
                    }
                } else {
                    // Otherwise, add the file directly to submissionFolder
                    submissionFolder.addComponent(new JavaFile(entryName, content, (destinationPath + "/" + entryName)));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return submissionFolder;
    }

    /**
     * Retrieves the Folder instance containing the extracted files and folders.
     *
     * @return The Folder instance.
     */
    @Override
    public Folder getFolder() {
        return this.submissionFolder;
    }
}

