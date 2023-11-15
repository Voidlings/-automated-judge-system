package com.voidlings;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.voidlings.AssignmentHandling.JavaClassInfo;
import com.voidlings.AssignmentHandling.JavaCodeAnalyzer;
import com.voidlings.AssignmentHandling.MethodInfo;
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

                            // Test Pilot
                            // Manually generate spec sheet and test if attributes are present.
                            System.out.println("\n" + "Testing");
                            // In another class, compare attributes to spec and come up with a grade.
                            List<ArrayList> specs = new ArrayList <ArrayList> ();

                            // LuggageManagementSystem Class
                            String [] s1 = {};
                            ArrayList a1 = new ArrayList<>(Arrays.asList(s1));
                            specs.add(a1);

                            // Flight class
                            String [] s2 = {"flightNo", "destination", "origin", "flightDate", "manifest"};
                            ArrayList a2 = new ArrayList<>(Arrays.asList(s2));
                            specs.add(a2);

                            // LuggageSlip class
                            String [] s3 = {"owner", "luggageSlipIDCounter", "luggageSlipID", "label"};
                            ArrayList a3 = new ArrayList<>(Arrays.asList(s3));
                            specs.add(a3);

                            // Passenger Class
                            String [] s4 = {"passportNumber", "flightNo", "firstName", "lastName", "numLuggage", "cabinClass"};
                            ArrayList a4 = new ArrayList<>(Arrays.asList(s4));
                            specs.add(a4);

                            // LuggageManifest Class
                            String [] s5 = {"slips"};
                            ArrayList a5 = new ArrayList<>(Arrays.asList(s5));
                            specs.add(a5);

                            // Make a method that goes through all the classes and marks to suit.
                            // Loop for each class, get spec, get assignment stuff and generate aggregate score.
                            AttributeTestCase t = new AttributeTestCase();

                            int i = 0;
                            int totalScore = 0;
                            int totalPossibleScore = 0;
                            for (JavaClassInfo c : classes){
                                int attributeGrade = t.attributeGrading(c.getAttributeNames(), specs.get(i));
                                System.out.println("The attribute grade for class " + c.getClassName() + ": " + attributeGrade + "/" + specs.get(i).size());
                                totalPossibleScore = specs.get(i).size() + totalPossibleScore;
                                totalScore = totalScore + attributeGrade;
                                i++;
                                // Line by line, add the grade to the PDF.
                            }
                            
                            // Calculate aggregate score and add to PDF.
                            System.out.println("The total grade for attributes gained was: " + totalScore + "/" + totalPossibleScore);

                            // Get list of method names.
                            List <String> methods = new ArrayList <String>();
                            for (MethodInfo methodInfo : classes.get(1).getMethodInfoMap().values()) {
                                // Add to list
                                methods.add(methodInfo.getMethodName());

                                // System.out.println("Method: " + methodInfo.getMethodName());

                                // System.out.println("  Return Type: " + methodInfo.getReturnType());
                            }

                            // Now compare the names of these methods against the spec.

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
