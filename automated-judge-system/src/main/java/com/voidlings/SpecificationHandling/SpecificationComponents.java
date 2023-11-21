package com.voidlings.SpecificationHandling;

import java.util.ArrayList;

public interface SpecificationComponents {
    
    public void addAttribute(String attribute);
    public void addMethod(String method);
    public void addMethodMark(String mark);
    public boolean removeAttribute(String attribute);
    public boolean removeMethod(String method);
    public int numAttributes();
    public int numMethods();
    public int numMarks();
    public void getAttributes(ArrayList<String> listOfattributes);
    public void getMethods(ArrayList<String> listOfmethods);
    public void getMethodMarks(ArrayList<String> listOfmarks);
    public void printAttributes();
    public void printMethods();
    public void printMarks();
    public ArrayList<String> getAllAttributes();
    public ArrayList<String> getAllMethods();
    public ArrayList<String> getAllMarks();
    public  String getClassName();
    public void setClassName(String name);

}