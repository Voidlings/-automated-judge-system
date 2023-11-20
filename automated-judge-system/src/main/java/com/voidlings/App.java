package com.voidlings;

import java.util.ArrayList;

import com.voidlings.FileHandling.*;
import com.voidlings.ReportHandling.*;
import com.voidlings.SpecificationHandling.*;
import com.voidlings.TestCases.*;
import com.voidlings.EvaluationHandling.*;


public class App {
    public static void main(String[] args) throws Exception {
      //NOTE FROM DOMINIQUE: License to use PDF table extraction API Spire.PDF DO NOT REMOVE
      //Note: this license expires on the 15 december 2023 thus this program will no longer work, there after unless a new license is made
       com.spire.license.LicenseProvider.setLicenseKey("license.elic.xml");
       

        String directoryPath= "testFiles.zip"; //replace with actual filepath name, test file for now

        String destinationPath= "Submissions"; //adds to submissions folder on same directory as the App.java file. Do not change this until further notice.
        
        ZIPFileHandler zipFileHandler = new ZIPFileHandler(destinationPath, destinationPath);

        Folder allSubmissions;

        
        // Check if the file format is a ZIP file
        if (!zipFileHandler.checkFormat(directoryPath)) {
            System.err.println("Invalid file format: ZIP File required.");
            return;
        }
        else{
            System.out.println("Valid ZIP file format.");
        }

        // Extract files from the ZIP file
        allSubmissions = zipFileHandler.extractFiles(directoryPath, destinationPath);
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
            return;
        }

        //reads the class names,methods, and attributes, and marks for each class to an arraylist specifcation classes(specification)
        SpecificationHandler specification = new SpecificationHandler("COMP2603  Assignment 1 Marking Scheme.pdf");
        ArrayList<SpecificationComponents> specificationClasses = specification.readSpecificationFile();
        /*
        for(SpecificationComponents specClass : specificationClasses){
            System.out.println(specClass.getClassName());
            specClass.printAttributes();
            specClass.printMethods();
            specClass.printMarks();
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        }
        */

        for (Folder folder : allSubmissions.getFolderList()) {
            ArrayList<String> javaFileNames= new ArrayList<>();
            javaFileNames.addAll(folder.getJavaFilePaths()); //get all file paths in each folder
        
            JavaHandler javahandler = new JavaHandler(javaFileNames);
            ArrayList<SpecificationComponents> javas = javahandler.readJava();
            
            /*
            for(SpecificationComponents java: javas){
                String name = java.getClassName();
                //System.out.println("Class Name: " + name);
                System.out.println("Attributes:");
                java.printAttributes();
                //System.out.println("Methods:");
                //java.printMethods();
                System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            }
            */

            // Grade each class against the specification.
            // Attributes are graded, 1 mark each per present attribute.
            AttributeTestCase gradeAttr = new AttributeTestCase(specificationClasses, javas);
           
            // Methods: Marks per method in ArrayList. If present, mark given.
            MethodTestCase gradeMethods = new MethodTestCase(specificationClasses, javas);
            // Sum method grades and attribute grades in order to get total grade.

            System.out.println("Total marks obtained from the assignment: " + (gradeAttr.getTotalScore() + gradeMethods.getTotalScore() + " marks")); 
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");

            ArrayList<AttributeEval> attrEval = gradeAttr.getAttributeEvals();
            ArrayList<MethodEval> methodEval = gradeMethods.getMethodEvals();

            /* 
            for (AttributeEval attr : attrEval){
              System.out.println("Attibute: " + attr.name + "\nPassed: " + attr.passed + "\nScore:" + attr.score + "\n");
              System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
            } */
            
            /* 
            for (MethodEval method : methodEval){
              System.out.println("Method: " + method.name + "\nPassed: " + method.passed + "\nScore:" + method.score + "\n");
              System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
            } */
            
            EvaluationReport report = new PDFEvaluationReport(attrEval, methodEval);
            Generator pdfGenerator = new PDFGenerator(report, folder.getName());
            pdfGenerator.generate();
        }
    }
}
