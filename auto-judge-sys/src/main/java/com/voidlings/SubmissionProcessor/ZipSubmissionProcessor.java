package com.voidlings;

import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.EnumSet;

public class ZipSubmissionProcessor implements SubmissionProcessor{
    private List<File> files;
    
    public void processSubmission(String submission) {
        return;
    }

    public Path findZipFileByName(String fileName) throws IOException {
        Path startingDir = Path.of("C:\\"); // You can change this to the root directory you want to start the search from

        FileVisitor<Path> fileVisitor = new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                if (file.getFileName().toString().equals(fileName) && file.toString().toLowerCase().endsWith(".zip")) {
                    return FileVisitResult.TERMINATE;
                }
                return FileVisitResult.CONTINUE;
            }
        };

        EnumSet<FileVisitOption> options = EnumSet.noneOf(FileVisitOption.class);
        Files.walkFileTree(startingDir, options, Integer.MAX_VALUE, fileVisitor);

        return null; // If the file is not found
    }

    public static void main(String[] args) {
        String fileNameToSearch = "example.zip"; // Change this to the name of the ZIP file you want to find
        try {
            Path zipFilePath = this.findZipFileByName(fileNameToSearch);
            if (zipFilePath != null) {
                System.out.println("ZIP file found at: " + zipFilePath);
            } else {
                System.out.println("ZIP file not found.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<File> getFiles () {
        return files; 
    }
}