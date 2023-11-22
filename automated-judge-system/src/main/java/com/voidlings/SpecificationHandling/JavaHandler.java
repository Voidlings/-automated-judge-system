package com.voidlings.SpecificationHandling;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * The JavaHandler class is responsible for handling Java files and extracting information
 * to create SpecificationComponents representing classes with their attributes and methods.
 * It uses a FileConverter to convert Java files to text before processing.
 *
 * @author Voidlings
 * @version 1.0
 */
public class JavaHandler {

    private ArrayList<SpecificationComponents> javaClasses = new ArrayList<>();

    private ArrayList<String> javaFiles;
    
    public FileConverter fileConverter;
    
    public SpecificationComponents AsgClass;

    /**
     * Constructs a JavaHandler with the given list of Java file paths.
     *
     * @param javaFiles The list of Java file paths to be processed.
     */
    public JavaHandler(ArrayList<String> javaFiles) {
        this.javaFiles = javaFiles;
    }

    /**
     * Creates a new SpecificationClass with the given class name.
     *
     * @param className The name of the class.
     * @return The SpecificationClass created.
     */
    public SpecificationClass createAssignmentClass(String className) {
        return new SpecificationClass(className);
    }

    /**
     * Gets the first word from a sentence by trimming and finding the first whitespace.
     *
     * @param sentence The sentence to extract the first word from.
     * @return The first word of the sentence.
     */
    public String firstWordGetter(String sentence) {
        String trimmedInput = sentence.trim();

        if (!trimmedInput.isEmpty()) {
            int firstWhitespaceIndex = trimmedInput.indexOf(' ');

            if (firstWhitespaceIndex == -1) {
                return trimmedInput;
            } else {
                return trimmedInput.substring(0, firstWhitespaceIndex);
            }
        } else {
            return "";
        }
    }

    /**
     * Gets the last character of a string.
     *
     * @param sentence The string to extract the last character from.
     * @return The last character of the string.
     */
    public char getLastCharacter(String sentence) {
        char lastChar;
        if (sentence != null && sentence.length() > 0) {
            lastChar = sentence.charAt(sentence.length() - 1);
            return lastChar;
        } else {
            return ' ';
        }
    }

    /**
     * Reads Java files, extracts information, and creates SpecificationComponents.
     *
     * @return The ArrayList of SpecificationComponents representing Java classes.
     * @throws Exception If an error occurs during file processing.
     */
    public ArrayList<SpecificationComponents> readJava() throws Exception {
        String firstWord;
        char lastChar;
        int methodCount;
        ArrayList<String> attributes = new ArrayList<>();

        FileConverter fileConverter = new JavatoText();
        for (String java : javaFiles) {
            methodCount = 0;
            String javaTXT = fileConverter.convert(java);
            AsgClass = createAssignmentClass("Temp");
            try {
                File file = new File(javaTXT);
                Scanner read = new Scanner(file);
                while (read.hasNextLine()) {
                    String data = read.nextLine();
                    if (data.isEmpty() && read.hasNextLine()) {
                        data = read.nextLine();
                    }
                    firstWord = firstWordGetter(data);
                    lastChar = getLastCharacter(data);
                    data = data.trim();

                    if (firstWord != null && (firstWord.equals("public") || firstWord.equals("private") || firstWord.equals("protected"))
                            && (lastChar == ')' || lastChar == '{')) {
                        methodCount++;

                        if (lastChar == '{') {
                            String noLastChar = data.substring(0, data.length() - 1);
                            if (methodCount == 1) {
                                AsgClass.setClassName(noLastChar);
                            }
                            AsgClass.addMethod(noLastChar);
                        } else {
                            attributes.add(data);
                            AsgClass.addMethod(data);
                        }
                    } else if (firstWord != null && (firstWord.equals("public") || firstWord.equals("private") || firstWord.equals("protected"))
                            && lastChar == ';') {
                        String noLastChar = data.substring(0, data.length() - 1);
                        AsgClass.addAttribute(noLastChar);
                    }
                }
                javaClasses.add(AsgClass);
            } catch (FileNotFoundException e) {
                System.out.println("File not found!");
            }
        }
        return javaClasses;
    }
}
