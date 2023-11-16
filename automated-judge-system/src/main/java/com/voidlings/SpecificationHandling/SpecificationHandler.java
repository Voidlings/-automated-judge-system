package com.voidlings.SpecificationHandling;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class SpecificationHandler {
    private ArrayList<SpecificationComponents> specificationClasses = new ArrayList<>();;
    private String specificationPDFFile;
    public FileConverter fileConverter;
    public SpecificationComponents AsgClass;

    public SpecificationHandler(String specPDF){
        specificationPDFFile = specPDF;
    }

    public SpecificationClass createAssignmentClass(String className){
        return new SpecificationClass(className);
    }

    public String getFirstWord(String sentence){
    
        int firstSpace = sentence.indexOf(" ");
        if(firstSpace != -1){
        String firstWord = sentence.substring(0, firstSpace);
        return firstWord;
        }
        else{
            return null;
        }
    }
    


    public String arrangeAttribute(String attribute){
        String[] words = attribute.split("\\|");
        String encapsulator = words[3];
        String[] moreWords = encapsulator.split(" ");
        encapsulator = moreWords[1];

        if(encapsulator.equals("Private,")){
            encapsulator = "private";
        }
        else{
            encapsulator = "public";
        }
        String arrangedAttribute = encapsulator + " " + words[1] + " " + words[0];

        return arrangedAttribute;
    }

    public String arrangeMethod(String method){
        String[] words = method.split("\\|");
        String encapsulator = "public";
        String arrangedMethod = encapsulator + " " + words[1] + " " + words[0];
        return arrangedMethod;
    }

    public String arrangeMark(String method){
        String words[] = method.split("\\|");
        List moreWords = new ArrayList<String>();
        moreWords = Arrays.asList(words);
        int i = 2;
        return  (String) moreWords.get(i);
    }




    public ArrayList<SpecificationComponents> readSpecificationFile() throws Exception{
        boolean isAttribute = false;
        boolean isMethod = false;
        boolean isMarkScheme = true;
        ArrayList<String> attributes = new ArrayList<>();
         ArrayList<String> methods = new ArrayList<>();
        ArrayList<String> classList = new ArrayList<>();
        int attributecount = -1;
        int classCount = 0;
        String  firstWord;
        fileConverter = new PDFtoText();
        String PDFtxt = fileConverter.convert(specificationPDFFile);
        try{
            File file = new File(PDFtxt);
            Scanner read = new Scanner(file);
            while(read.hasNextLine()){
                String data = read.nextLine();
                // reads the first word searching for Keywords Attribute + Method
                firstWord = getFirstWord(data);
                

                //read mark scheme table
                if(isMarkScheme == true){
                    if(!firstWord.equals("Class") && !firstWord.equals("Total")){
                    classList.add(firstWord);
                }
                if(firstWord.equals("Total")){
                    isMarkScheme = false;
                }
                }
 
                //Switching from reading Methods to Attributes
                if(firstWord.equals("Method")){
                    isMethod = true;
                    isAttribute = false;
                }
                else if(firstWord.equals("Attribute")){
                     attributecount++;
                     if(classCount > 0){
                        specificationClasses.add(AsgClass);
                     }
                     AsgClass =  createAssignmentClass(classList.get(attributecount));//new SpecificationClass(classList.get(attributecount));
                     classCount++;
                     
                    
                    
                    //AsgClass = specificationClasses.get(attributecount);
                    isMethod = false;
                    isAttribute = true;
                }
            

               //Pulling Attributes
                if(isAttribute == true){
                    if(firstWord.equals("Attribute")){
                        data = read.nextLine();
                    }
                    data = arrangeAttribute(data);
                    AsgClass.addAttribute(data);
                    attributes.add(data);
                }

                //Pulling methods
                if(isMethod == true){
                    if(firstWord.equals("Method")){
                        data = read.nextLine();
                    }
                    String mark = arrangeMark(data);
                    AsgClass.addMethodMark(mark);
                    data = arrangeMethod(data);
                    AsgClass.addMethod(data);
                    methods.add(data);
                }
                
            
            }
            specificationClasses.add(AsgClass);
            read.close();  
            
        }catch(FileNotFoundException e){
            System.out.println("File not found!");
        }

     return specificationClasses;
    }  

}
