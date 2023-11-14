package com.voidlings;

/* Note until we figure out automating the addition of maven packaging to the extracted files
please delete the extracted submission folder before running the code again*/

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.List;

import com.voidlings.FileHandling.FileComponent;
import com.voidlings.FileHandling.Folder;
import com.voidlings.FileHandling.JavaFile;
import com.voidlings.FileHandling.ZIPFileHandler;

public class App{
    public static void main(String[] args){
        /*To be replaced with Scanner state {
        /*To be replaced with Scanner statement asking for actual zip file path,
        we can use testFiles.zip to test the system for now */
        
        
        // Getting absolute path of Submission
        String filePath = Paths.get("Jasmine_Dottin_816033642_A1.zip").toAbsolutePath().toString();
        
        System.out.println("\n");
        System.out.println(filePath + "\n");

        String destinationPath = "Submissions"; // replace with actual destination directory path

        ZIPFileHandler zipFileHandler = new ZIPFileHandler(destinationPath, destinationPath);

        // Check if the file format is a ZIP file
        if (zipFileHandler.checkFormat(filePath)) {
            System.out.println("Valid ZIP file format.");

            // Extract files from the ZIP file
            Folder allSubmissions = zipFileHandler.extractFiles(filePath, destinationPath);
            if ((allSubmissions != null) && (allSubmissions.getFileComponents() != null) && (allSubmissions.getFolderList() != null)) {
                // Uncomment to test heirachy of folder and files
                /*
                for (Folder folder : allSubmissions.getFolderList()) {
                    System.out.println(folder.getName());
                    for(JavaFile f: folder.getJavaFiles())
                    System.out.println(folder.getName() + ": " + f.getName());
                }*/
                
                System.out.println("Extraction successful.");
                System.out.println("\n");

                // Trying out test where classes are checked.
                // Added new method to check if file exists in folder.
                // This can be used in the marking stage to test if all 
                // required classes are present.
                // Testing Pilot

                String[] requiredClasses = {"Flight.java", "Passenger.java", "LuggageManifest.java", "LuggageSlip.java", "LuggageManagementSystem.java"};
                int numClassesToPass = requiredClasses.length;
                int numPassed = 0;

                for (String s: requiredClasses){
                    allSubmissions.fileExistsRevised(s);
                    numPassed++;
                }

                System.out.println(numPassed + " out of " + numClassesToPass + " required classes are present. Score is : " + numPassed + "/" + numClassesToPass);

            } else {
                System.err.println("Extraction failed. Ensure that the zip contains folder submissions with .java files");
            }
        } else {
            System.err.println("Invalid file format: ZIP File required.");
        }
    }

}
