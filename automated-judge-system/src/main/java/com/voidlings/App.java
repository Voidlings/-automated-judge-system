package com.voidlings;

import java.io.File;
import com.voidlings.FileHandling.Folder;
import com.voidlings.FileHandling.ZIPFileHandler;

public class App {
    public static void main(String[] args) {
      //NOTE FROM DOMINIQUE: License to use PDF table extraction API Spire.PDF DO NOT REMOVE
      //Note: this license expires on the 15 december 2023 thus this program will no longer work, there after unless a new license is made
       com.spire.license.LicenseProvider.setLicenseKey("license.elic.xml");
       


        String directoryPath = "/workspaces/-automated-judge-system/automated-judge-system/src/main/java/com/voidlings/AssignmentSubzip";

        String destinationPath = "submissions";

        File directory = new File(directoryPath);

        if (directory.isDirectory()) {
            File[] files = directory.listFiles();

            for (File file : files) {
                if (file.isFile() && file.getName().toLowerCase().endsWith(".zip")) {
                    ZIPFileHandler zipFileHandler = new ZIPFileHandler(destinationPath, destinationPath);

                    if (zipFileHandler.checkFormat(file.getAbsolutePath())) {
                        System.out.println("Valid ZIP file format for file: " + file.getName());

                        Folder allSubmissions = zipFileHandler.extractFiles(file.getAbsolutePath(), destinationPath);

                        if ((allSubmissions != null) && (allSubmissions.getFileComponents() != null)
                                && (allSubmissions.getFolderList() != null)) {
                            // Uncomment to test hierarchy of folder and files
                            /* 
                              for (Folder folder : allSubmissions.getFolderList()) {
                              System.out.println(folder.getName()); for(JavaFile f:
                              folder.getJavaFiles())
                              System.out.println(folder.getName() + ": " + f.getName()); }
                              */
                             
                            System.out.println("Extraction successful for file: " + file.getName());
                        } else {
                            System.err.println("Extraction failed for file: " + file.getName() +
                                    ". Ensure that the zip contains folder submissions with .java files");
                        }
                    } else {
                        System.err.println("Invalid file format for file: " + file.getName() + ". ZIP File required.");
                    }
                }
            }
        } else {
            System.err.println("Specified path is not a directory.");
        }
    }
}
