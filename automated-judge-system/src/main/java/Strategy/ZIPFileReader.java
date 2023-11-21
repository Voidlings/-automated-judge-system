package Strategy;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import com.voidlings.FileHandling.FileComponent;
import com.voidlings.FileHandling.Folder;
import com.voidlings.FileHandling.JavaFile;

public class ZIPFileReader implements ReadingStrategy {
    private Folder submissionFolder;

    @Override
    public Boolean checkFormat(String path) {
        // Check if the file has a .zip extension
        return path.toLowerCase().endsWith(".zip");
    }

    public Boolean containsFileType(String filePath) {
        try (ZipInputStream zipIn = new ZipInputStream(new FileInputStream(filePath))) {
            ZipEntry entry;
            while ((entry = zipIn.getNextEntry()) != null) {
                if (!entry.isDirectory() && entry.getName().endsWith(".java")) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Folder extractFiles(String zipFilePath, String destinationPath) {
        if (!containsFileType(zipFilePath)) {
            return null;
        }

        File outputFolder = submissionFolder.createFolder(destinationPath);

        if (outputFolder == null) {
            System.err.println("Failed to create the destination directory.");
            return null;
        }

        // extract all the files in the zip
        try (ZipInputStream zipIn = new ZipInputStream(new FileInputStream(zipFilePath))) {
            ZipEntry entry;
            while ((entry = zipIn.getNextEntry()) != null) {
                String entryName = entry.getName();

                if (entry.isDirectory()) {
                    // Skip directories
                    System.out.println("Directory Encountered: " + entryName);
                    continue;
                }

                if (!entryName.endsWith(".java")) {
                    // Skip non-Java files
                    System.out.println("Non-Java File Skipped: " + entryName);
                    continue;
                }

                File outputFile = new File(destinationPath, entryName);

                if (!outputFile.getParentFile().exists()) {
                    // ensure directories are made for the files
                    outputFile.getParentFile().mkdirs();
                }

                // use array buffer to read entry
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = zipIn.read(buffer)) != -1) {
                    bos.write(buffer, 0, bytesRead);
                }

                String content = new String(bos.toByteArray());// content for SubmissionFile

                if (entryName.contains("/")) {
                    // If the entry is in a subdirectory, create nested folders
                    String[] folders = entryName.split("/");
                    Folder currentFolder = this.submissionFolder;

                    for (int i = 0; i < folders.length - 1; i++) {
                        // Check if the folder already exists
                        FileComponent existingComponent = currentFolder.getComponentByName(folders[i]);
                        if (existingComponent == null) {
                            currentFolder.addComponent(new Folder(folders[i], (destinationPath + "/" + folders[i])));
                            existingComponent = currentFolder.getComponentByName(folders[i]);
                        }
                        currentFolder = (Folder) existingComponent;
                    }
                    currentFolder.addComponent(
                            new JavaFile(folders[folders.length - 1], content, (destinationPath + "/" + entryName)));

                    // Write the content to the outputFile
                    try (FileOutputStream fos = new FileOutputStream(outputFile)) {
                        fos.write(("package com.voidlings.Submissions." + currentFolder.getName() + ";\n").getBytes());
                        fos.write(bos.toByteArray());
                    }
                } else {
                    // Otherwise, add file directly to submissionFolder
                    submissionFolder
                            .addComponent(new JavaFile(entryName, content, (destinationPath + "/" + entryName)));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return submissionFolder;
    }

    public Folder getFolder() {
        return this.submissionFolder;
    }
}
