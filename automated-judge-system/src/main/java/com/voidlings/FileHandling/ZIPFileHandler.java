package com.voidlings.FileHandling;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ZIPFileHandler implements FileHandler{
    private Folder submissionFolder;

    public ZIPFileHandler(String folderName, String path){
        submissionFolder= new Folder(folderName, path);
    }

    @Override
    public Boolean checkFormat(String path) {
        // Check if the file exists
        File file = new File(path);
        if (!file.exists() || !file.isFile()) {
            return false;
        }
        //Checks if the format of the file is zip
        path = path.toLowerCase();

        if(path.endsWith(".zip"))
            return true;
        
        return false;
    }

    @Override
    public Boolean containsFileType(String filePath) {
        //checks if the zip contains java files
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
                File outputFile = new File(destinationPath, entryName);

                if (entry.isDirectory()) {
                    ZipInputStream dirStream= new ZipInputStream(new FileInputStream(zipFilePath));//new stream for use in isDirectoryEmpty so current one is not affected
                    if(isDirectoryEmpty(dirStream, entry)){
                        System.out.println("Skipping empty directory encountered: " + entryName);
                    }
                    else{
                        if(!outputFile.exists() && !outputFile.mkdirs())
                            System.err.println("Failed to create directory: " + outputFile.getAbsolutePath());
                       continue;
                    }
                }
                else{
                    // For regular files, ensure parent directories are created
                    if (!outputFile.getParentFile().exists()) {
                        outputFile.getParentFile().mkdirs();
                    }
                    
                    // use array buffer to read entry
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    byte[] buffer = new byte[4096];
                    int bytesRead;
                    while ((bytesRead = zipIn.read(buffer)) != -1) {
                        bos.write(buffer, 0, bytesRead);
                    }

                    if (!entryName.endsWith(".java")) {
                        // if non-Java file encountered only add it to the destination directory
                        try (FileOutputStream fos = new FileOutputStream(outputFile)) {
                            fos.write(bos.toByteArray());
                        }
                    }
                    else{
                        //if java file encountered, add to the destination directory and create JavaFile instances for addition to submissionFolder
                        String content = new String(bos.toByteArray());// content for JavaFile

                        
                        if (entryName.contains("/")) {
                            // If the entry is in a subdirectory, create nested folders
                            String[] folders = entryName.split("/");
                            Folder currentFolder = this.submissionFolder;

                            for (int i = 0; i < folders.length - 1; i++) {
                                // Check if the folder already exists
                                FileComponent existingComponent = currentFolder.getComponentByName(folders[i]);
                                if (existingComponent == null) {
                                    currentFolder.addComponent(new Folder(folders[i], (destinationPath + "/"+ folders[i])));
                                    existingComponent = currentFolder.getComponentByName(folders[i]);
                                }
                                currentFolder = (Folder) existingComponent;
                            }
                            currentFolder.addComponent(new JavaFile(folders[folders.length - 1], content, (destinationPath + "/" + entryName)));

                            // Write the content to the outputFile
                            try (FileOutputStream fos = new FileOutputStream(outputFile)) {
                                String packageStatement = getPackageStatement(currentFolder, entryName);
                                fos.write(packageStatement.getBytes());
                                fos.write(bos.toByteArray());
                            }
                        } else {
                            // Otherwise, add file directly to submissionFolder
                            submissionFolder.addComponent(new JavaFile(entryName, content, (destinationPath + "/" + entryName)));
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return submissionFolder;
    }

    @Override
    public Folder getFolder() {
        return this.submissionFolder;
    }

    private boolean isDirectoryEmpty(ZipInputStream zipInputStream, ZipEntry entry) {
        //check whether directory is empty
        String directoryPath = entry.getName();
        boolean isEmpty = true;
    
        try {
            // Iterate through all entries in the ZIP file
            ZipEntry zipEntry;
            while ((zipEntry = zipInputStream.getNextEntry()) != null) {
                String entryPath = zipEntry.getName();
    
                if (!zipEntry.isDirectory()){//if entry is on the directory path then directory is not empty
                    if(entryPath.startsWith(directoryPath)){
                        isEmpty= false;
                        return isEmpty;
                    }
                    else{
                        isEmpty= true;
                    }
                }
            }
        } catch (IOException e) {
            return true; // Treat as empty if an exception occurs
        }
    
        return isEmpty; // Directory and its subdirectories are empty
    }
    

    private String getPackageStatement(Folder currentFolder, String entryName) {
        //method to get the appropriate statement for the package 
        String[] folders = entryName.split("/");
    
        StringBuilder packageStatement = new StringBuilder("package com.voidlings.submissions");
    
        // Exclude the last element (filename) from the loop
        for (int i = 0; i < folders.length - 1; i++) {
            packageStatement.append('.').append(folders[i]);
        }
    
        packageStatement.append(";\n");
        return packageStatement.toString();
    }
    
}
