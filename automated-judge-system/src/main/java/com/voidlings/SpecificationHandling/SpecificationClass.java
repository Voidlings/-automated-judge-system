package com.voidlings.SpecificationHandling;


import java.util.ArrayList;

public class SpecificationClass implements SpecificationComponents{

private String className;
public ArrayList<String> attributes = new ArrayList<>();
public ArrayList<String> methods = new ArrayList<>();
public ArrayList<String> methodMarks = new ArrayList<>();

public SpecificationClass(String className){
    this.className = className;
}



    public void addAttribute(String attribute) {
        attributes.add(attribute);
    }

    
    public void addMethod(String method) {
        methods.add(method);
    }

   
    public void addMethodMark(String mark) {
        methodMarks.add(mark);
    }


    public boolean removeAttribute(String attribute) {
        if(attributes.contains(attribute)){
            attributes.remove(attribute);
            return true;
        }
        return false;
    }

    
    public boolean removeMethod(String method) {
         if(methods.contains(method)){
            methods.remove(method);
            return true;
        }
        return false;
    } 


    public int numAttributes() {
        return attributes.size();
    }

   
    public int numMethods() {
       return methods.size();
    }



    public int numMarks() {
        return methodMarks.size();
    }


    
    public void getAttributes(ArrayList<String> listOfattributes) {
        this.attributes = listOfattributes;
    }


     
    public void getMethods(ArrayList<String> listOfmethods) {
        this.methods = listOfmethods;
    }

   
    public void getMethodMarks(ArrayList<String> listOfmarks) {
        this.methodMarks = listOfmarks;
    }

    
    public void printAttributes() {
         for(String attribute: attributes){
        System.out.println(attribute);
      }
    } 


    
    public void printMethods() {
      for(String method: methods){
        System.out.println(method);
      }
    }



  
    public void printMarks() {
       for(String mark: methodMarks){
        System.out.println(mark);
      }
    }



    @Override
    public ArrayList<String> getAllAttributes() {
        return this.attributes;
    }



    @Override
    public ArrayList<String> getAllMethods() {
        return this.methods;
    }



    @Override
    public ArrayList<String> getAllMarks() {
        return this.methodMarks;
    }



    @Override
    public String getClassName() {
        return className;
    }

    public void setClassName(String name){
        this.className = name;
    }

    

    
    
}