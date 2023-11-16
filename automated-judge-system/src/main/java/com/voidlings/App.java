package com.voidlings;

import java.io.File;
import java.util.ArrayList;

import com.voidlings.FileHandling.*;

public class App {
    public static void main(String[] args) {
      //NOTE FROM DOMINIQUE: License to use PDF table extraction API Spire.PDF DO NOT REMOVE
      //Note: this license expires on the 15 december 2023 thus this program will no longer work, there after unless a new license is made
       com.spire.license.LicenseProvider.setLicenseKey("license.elic.xml");
       

        String directoryPath= "testFiles.zip"; //replace with actual filepath name, test file for now

        String destinationPath= "submissions"; //adds to submissions folder on same directory as the App.java file. Do not change this until further notice.
        
        ZIPFileHandler zipFileHandler = new ZIPFileHandler(destinationPath, destinationPath);

        
        // Check if the file format is a ZIP file
        if (zipFileHandler.checkFormat(directoryPath)) {
            System.out.println("Valid ZIP file format.");

            // Extract files from the ZIP file
            Folder allSubmissions = zipFileHandler.extractFiles(directoryPath, destinationPath);
            if ((allSubmissions != null) && (allSubmissions.getFileComponents() != null) && (allSubmissions.getFolderList() != null)) {
                // Uncomment to test heirachy of folder and files
                /*for (Folder folder : allSubmissions.getFolderList()) {
                        System.out.println(folder.getName());
                    for(JavaFile f: folder.getJavaFiles())
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
