package com.voidlings;
import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ZIPFileHandler implements FileHandler{
    private Folder submissionFolder;

    public ZIPFileHandler(String folderName){
        submissionFolder= new Folder(folderName);
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
        if(!containsFileType(zipFilePath)){
            return null;
        }

        File outputFolder = submissionFolder.createFolder(destinationPath);

        if (outputFolder == null) {
            System.err.println("Failed to create the destination directory.");
            return null;
        }

        //extract all the files on the zip
        try (ZipInputStream zipIn = new ZipInputStream(new FileInputStream(zipFilePath))) {
            ZipEntry entry;
            while ((entry = zipIn.getNextEntry()) != null) {
                String entryName = entry.getName();
                File outputFile = new File(destinationPath, entryName);
            
                if (!outputFile.getParentFile().exists()) { //ensure directories are made for the files
                    outputFile.getParentFile().mkdirs();
                }

                if (!entry.isDirectory()) { //use array buffer to read entry
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    byte[] buffer = new byte[4096];
                    int bytesRead;
                    while ((bytesRead = zipIn.read(buffer)) != -1) {
                        bos.write(buffer, 0, bytesRead);
                    }

                    String content = new String(bos.toByteArray());//content for SubmissionFile

                    /*Facillate adding of folders and files to the main Folder instance; submissionsFolder
                    */
                    if (entryName.contains("/")) {
                        //If the entry is in a subdirectory, create nested folders
                        String[] folders = entryName.split("/");
                        Folder currentFolder = this.submissionFolder;

                        for (int i = 0; i < folders.length - 1; i++) {
                            // Check if the folder already exists
                            Folder existingFolder = currentFolder.getSubFolderByName(folders[i]);
                            if (existingFolder == null) {
                                currentFolder.addFolder(new Folder(folders[i]));
                                existingFolder = currentFolder.getSubFolderByName(folders[i]);
                            }
                            currentFolder = existingFolder;
                        }
                        currentFolder.addFile(new SubmissionFile(folders[folders.length - 1], content));
                        
                        // Write the content to the outputFile
                        try (FileOutputStream fos = new FileOutputStream(outputFile)) {
                            if(entryName.endsWith(".java"))
                                fos.write(("package com.voidlings.submissions." + currentFolder.getName() + ";\n")
                                        .getBytes());
                            fos.write(bos.toByteArray());
                        }
                    }
                    else {
                        // Otherwise, add file directly to submissionFolder
                        submissionFolder.addFile(new SubmissionFile(entryName, content));
                    }
                }
                else{
                    System.out.println("FOLDER");
                }
            }
        }catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return submissionFolder;
    }

    @Override
    public Folder getFolder() {
        return this.submissionFolder;
    }
    
}