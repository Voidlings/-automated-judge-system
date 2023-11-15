package com.voidlings;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.voidlings.AssignmentHandling.JavaClassInfo;
import com.voidlings.AssignmentHandling.JavaCodeAnalyzer;
import com.voidlings.FileHandling.FileComponent;
import com.voidlings.FileHandling.Folder;
import com.voidlings.FileHandling.JavaFile;
import com.voidlings.FileHandling.ZIPFileHandler;
import com.voidlings.TestCases.AttributeTestCase;

public class App {
    public static void main(String[] args) {
        // Absolute path:
        String directoryPath = "/Users/jasmine/Documents/GitHub/-automated-judge-system/automated-judge-system/src/main/java/com/voidlings/AssignmentSubzip";

        String destinationPath = "Submissions";

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
                                System.out.println(folder.getName());
                                for(JavaFile f: folder.getJavaFiles()){
                                System.out.println(folder.getName() + ": " + f.getName()); 
                                }
                            }
                            */

                            System.out.println("Extraction successful for file: " + file.getName());

                            // Get attributes and methods and put into the proper classes for evaluation process.
                            // Analyze the submission.
                            List <JavaClassInfo> classes = JavaCodeAnalyzer.processSubmissionFiles("/Users/jasmine/Documents/GitHub/-automated-judge-system/automated-judge-system/src/main/java/com/voidlings/Submissions");
                            
                            /* Successfully extracted class information
                            for (JavaClassInfo j : classes){
                                System.out.println("Class Name: " + j.getClassName());
                                System.out.println("Attributes: ");

                                for (String a : j.getAttributeNames()){
                                    System.out.println("\t\t" + a.toString());
                                }
                                System.out.println("\n");
                            }
                            */

                            // Testing Pilot
                            System.out.println("\n" + "Testing");
                            // In another class, compare attributes to spec and come up with a grade.
                            String [] sampleAttr = {"flightNo", "destination", "origin", "flightDate", "manifest"};

                            List <String> sampleAttrSpec = new ArrayList<String>();
                            for (String s : sampleAttr){
                                sampleAttrSpec.add(s);
                            }

                            // For the Flight class.
                            AttributeTestCase t = new AttributeTestCase();
                            int attributeGrade = t.attributeGrading(classes.get(1).getAttributeNames(), sampleAttrSpec);
                            System.out.println("The attribute grade is: " + attributeGrade + "/" + sampleAttrSpec.size());

                            // Export that grade to PDF.
                            // The overview will give comments based on the grade received - good, very good, fair, etc.
                        
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
