package com.voidlings;

/* Note until we figure out automating the addition of maven packaging to the extracted files
please delete the extracted submission folder before running the code again*/

import java.io.File;
import java.util.List;

public class App{
    public static void main(String[] args) {
        /*To be replaced with Scanner statement asking for actual zip file path,
        we can use testFiles.zip to test the system for now */
        String filePath = "testFiles.zip";

        String destinationPath = "submissions"; // replace with actual destination directory path

        ZIPFileHandler zipFileHandler = new ZIPFileHandler(destinationPath);

        // Check if the file format is a ZIP file
        if (zipFileHandler.checkFormat(filePath)) {
            System.out.println("Valid ZIP file format.");

            // Extract files from the ZIP file
            Folder allSubmissions = zipFileHandler.extractFiles(filePath, destinationPath);
            if ((allSubmissions != null) && (allSubmissions.getFiles() != null) && (allSubmissions.getFolderList() != null)) {
                // Testing heirachy of folder and file classes
                /*for (Folder folder : allSubmissions.getFolderList()) {
                    System.out.println(folder.getName());
                    for(SubmissionFile f: folder.getFiles())
                    System.out.println(folder.getName() + ": " + f.getName());
                }*/
                System.out.println("Extraction successful.");
            } else {
                System.err.println("Extraction failed. Ensure that the zip contains folder submissions with .java files");
            }
        } else {
            System.err.println("Invalid file format: ZIP File required.");
        }
    }
}
