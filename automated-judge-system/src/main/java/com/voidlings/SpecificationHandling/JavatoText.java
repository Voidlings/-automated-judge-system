package com.voidlings.SpecificationHandling;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * The JavatoText class implements the FileConverter interface to convert Java files to text files.
 *
 * @author Voidlings
 * @version 1.0
 */
public class JavatoText implements FileConverter {

    /**
     * Default constructor for JavatoText.
     */
    public JavatoText() {
    }

    /**
     * Converts the given Java file to a text file and returns the path of the generated text file.
     *
     * @param filename The path of the Java file to be converted.
     * @return The path of the generated text file.
     * @throws Exception If an error occurs during the conversion process.
     */
    @Override
    public String convert(String filename) throws Exception {
        File file = new File(filename);
        String path = file.getPath();

        // Provide the path to the input Java file
        String inputFilePath = path;

        // Provide the path to the output text file
        String outputFilePath = System.getProperty("user.dir") + "/" + filename + ".txt";

        try {
            // Read the content of the Java file
            String javaCode = readJavaFile(inputFilePath);

            // Write the Java code to the text file
            writeTextFile(outputFilePath, javaCode);

            // System.out.println("Conversion successful. Java file converted to text file.");
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
        return outputFilePath;
    }

    /**
     * Reads the content of the Java file.
     *
     * @param filePath The path of the Java file.
     * @return The content of the Java file as a string.
     * @throws IOException If an error occurs during file reading.
     */
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

    /**
     * Writes the given content to a text file.
     *
     * @param filePath The path of the text file to be written.
     * @param content  The content to be written to the text file.
     * @throws IOException If an error occurs during file writing.
     */
    private static void writeTextFile(String filePath, String content) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(content);
        }
    }
}
