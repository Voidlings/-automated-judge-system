package com.voidlings.SpecificationHandling;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class JavaHandler {
    private ArrayList<SpecificationComponents> javaClasses = new ArrayList<>();;
    private ArrayList<String> javaFiles;
    public FileConverter fileConverter;
    public SpecificationComponents AsgClass;

    public JavaHandler(ArrayList<String> javaFiles){
        this.javaFiles = javaFiles;
    }

    public SpecificationClass createAssignmentClass(String className){
        return new SpecificationClass(className);
    }

    public String firstWordGetter(String sentence){
         // Trim leading and trailing whitespaces
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


    

    public char getLastCharacter(String sentence){
        char lastChar;
        if(sentence != null && sentence.length() > 0){
            lastChar = sentence.charAt(sentence.length() - 1);

            return lastChar;
        }
        else{
            char line = ' ';
            return line;
        }
    }

    public ArrayList<SpecificationComponents> readJava() throws Exception{
        String firstWord;
        char lastChar;
        int methodCount;
        ArrayList<String> attributes = new ArrayList<>();
        
        FileConverter fileConverter = new JavatoText();
        for(String java: javaFiles){
            methodCount = 0;
            String javaTXT = fileConverter.convert(java);
            AsgClass = createAssignmentClass("Temp");
            try{
                 File file = new File(javaTXT);
                 Scanner read = new Scanner(file);
                 while(read.hasNextLine()){
                    String data = read.nextLine();
                    if(data.isEmpty()){
                        data = read.nextLine();
                    }
                    // reads the first word searching for Keywords Attribute + Method
                    firstWord = firstWordGetter(data);
                    lastChar = getLastCharacter(data);
                    data = data.trim();
                 

                    //check for methods
                    if(firstWord != null && (firstWord.equals("public")|| firstWord.equals("private")|| firstWord.equals("protected"))&& (lastChar == ')' ||lastChar == '{')){    
                       methodCount++;
                       
                        if(lastChar == '{'){
                            
                            String noLastChar = data.substring(0,data.length()- 1);
                            if(methodCount == 1){
                            AsgClass.setClassName(noLastChar);
                       }
                           // attributes.add(noLastChar);
                            AsgClass.addMethod(noLastChar);
                            
                        }
                        else{
                            attributes.add(data);
                            AsgClass.addMethod(data);  
                        } 
                    }
                    //checks for attributes
                    else if(firstWord != null && (firstWord.equals("public")|| firstWord.equals("private")|| firstWord.equals("protected"))&& lastChar == ';'){
                        String noLastChar = data.substring(0,data.length()- 1);
                        AsgClass.addAttribute(noLastChar);

                    }
                   
                }
              

            javaClasses.add(AsgClass);
            }
            catch(FileNotFoundException e){
                System.out.println("File not found!");
            }
        }
        return javaClasses;
    }
}
