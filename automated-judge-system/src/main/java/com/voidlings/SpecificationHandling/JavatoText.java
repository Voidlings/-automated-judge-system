package com.voidlings.SpecificationHandling;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class JavatoText implements FileConverter {

    public JavatoText(){
        
    }
    
    public String convert(String filename) throws Exception{
        File file = new File(filename);
        String path = file.getPath();
        // Provide the path to the input Java file
        String inputFilePath = path;

        // Provide the path to the output text file
        String outputFilePath = "/Users/owner/Downloads/comp3607testing/" + filename + ".txt";

        try {
            // Read the content of the Java file
            String javaCode = readJavaFile(inputFilePath);

            // Write the Java code to the text file
            writeTextFile(outputFilePath, javaCode);

            System.out.println("Conversion successful. Java file converted to text file.");
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
        return outputFilePath;
    }
    private static String readJavaFile(String filePath) throws IOException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        }
        return content.toString();
    }
    private static void writeTextFile(String filePath, String content) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(content);
        }
    }
    
}