package com.voidlings.SpecificationHandling;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * The SpecificationHandler class is responsible for handling the specification file.
 * It reads the specification file, extracts information about classes, attributes, methods,
 * and method marks, and creates SpecificationClass instances for each class in the assignment.
 *
 * @author Voidlings
 * @version 1.0
 */
public class SpecificationHandler {

    private ArrayList<SpecificationComponents> specificationClasses = new ArrayList<>();
    private String specificationPDFFile;
    public FileConverter fileConverter;
    public SpecificationComponents AsgClass;

    /**
     * Constructor for SpecificationHandler.
     *
     * @param specPDF The path to the specification PDF file.
     */
    public SpecificationHandler(String specPDF) {
        specificationPDFFile = specPDF;
    }

    /**
     * Creates a SpecificationClass instance with the given class name.
     *
     * @param className The name of the specification class.
     * @return The SpecificationClass instance.
     */
    public SpecificationClass createAssignmentClass(String className) {
        return new SpecificationClass(className);
    }

    /**
     * Extracts the first word from a sentence.
     *
     * @param sentence The input sentence.
     * @return The first word in the sentence.
     */
    public String getFirstWord(String sentence) {
        int firstSpace = sentence.indexOf(" ");
        if (firstSpace != -1) {
            String firstWord = sentence.substring(0, firstSpace);
            return firstWord;
        } else {
            return null;
        }
    }

    /**
     * Arranges the attribute in a specified format.
     *
     * @param attribute The attribute to be arranged.
     * @return The arranged attribute.
     */
    public String arrangeAttribute(String attribute) {
        String[] words = attribute.split("\\|");
        String encapsulator = words[3];
        String[] moreWords = encapsulator.split(" ");
        encapsulator = moreWords[1];

        if (encapsulator.equals("Private,")) {
            encapsulator = "private";
        } else {
            encapsulator = "public";
        }
        String arrangedAttribute = encapsulator + " " + words[1] + " " + words[0];

        return arrangedAttribute;
    }

    /**
     * Arranges the method in a specified format.
     *
     * @param method The method to be arranged.
     * @return The arranged method.
     */
    public String arrangeMethod(String method) {
        String[] words = method.split("\\|");
        String encapsulator = "public";
        String arrangedMethod = encapsulator + " " + words[1] + " " + words[0];
        return arrangedMethod;
    }

    /**
     * Arranges the method mark in a specified format.
     *
     * @param method The method mark to be arranged.
     * @return The arranged method mark.
     */
    public String arrangeMark(String method) {
        String words[] = method.split("\\|");
        List<String> moreWords = new ArrayList<>(Arrays.asList(words));
        int i = 2;
        return moreWords.get(i);
    }

    /**
     * Reads the specification file, extracts information about classes, attributes, methods,
     * and method marks, and creates SpecificationClass instances for each class in the assignment.
     *
     * @return The list of SpecificationClass instances.
     * @throws Exception If an error occurs during reading.
     */
    public ArrayList<SpecificationComponents> readSpecificationFile() throws Exception {
        boolean isAttribute = false;
        boolean isMethod = false;
        boolean isMarkScheme = true;
        ArrayList<String> attributes = new ArrayList<>();
        ArrayList<String> methods = new ArrayList<>();
        ArrayList<String> classList = new ArrayList<>();
        int attributeCount = -1;
        int classCount = 0;
        String firstWord;
        fileConverter = new PDFtoText();
        String PDFtxt = fileConverter.convert(specificationPDFFile);
        try {
            File file = new File(PDFtxt);
            Scanner read = new Scanner(file);
            while (read.hasNextLine()) {
                String data = read.nextLine();
                // reads the first word searching for Keywords Attribute + Method
                firstWord = getFirstWord(data);
    
                //read mark scheme table
                if (isMarkScheme == true) {
                    if (!firstWord.equals("Class") && !firstWord.equals("Total")) {
                        classList.add(firstWord);
                    }
                    if (firstWord.equals("Total")) {
                        isMarkScheme = false;
                    }
                }
    
                //Switching from reading Methods to Attributes
                if (firstWord.equals("Method")) {
                    isMethod = true;
                    isAttribute = false;
                } else if (firstWord.equals("Attribute")) {
                    attributeCount++;
                    if (classCount > 0) {
                        specificationClasses.add(AsgClass);
                    }
                    AsgClass = createAssignmentClass(classList.get(attributeCount));
                    classCount++;
                    isMethod = false;
                    isAttribute = true;
                }
    
                //Pulling Attributes
                if (isAttribute == true) {
                    if (firstWord.equals("Attribute")) {
                        data = read.nextLine();
                    }
                    data = arrangeAttribute(data);
                    AsgClass.addAttribute(data);
                    attributes.add(data);
                }
    
                //Pulling methods
                if (isMethod == true) {
                    if (firstWord.equals("Method")) {
                        data = read.nextLine();
                    }
                    String mark = arrangeMark(data);
                    AsgClass.addMethodMark(mark);
                    data = arrangeMethod(data);
                    AsgClass.addMethod(data);
                    methods.add(data);
                }
            }
    
            // Add the last class if it has not been added yet
            if (specificationClasses.isEmpty() || !specificationClasses.get(specificationClasses.size() - 1).equals(AsgClass)) {
                specificationClasses.add(AsgClass);
            }
    
            read.close();
    
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
        }
    
        return specificationClasses;
    }
}
