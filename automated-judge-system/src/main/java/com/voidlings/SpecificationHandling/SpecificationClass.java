package com.voidlings.SpecificationHandling;

import java.util.ArrayList;

/**
 * Represents a class in a software specification, including its attributes, methods, and associated marks.
 */
public class SpecificationClass implements SpecificationComponents {

    private String className;

    /** List of attributes associated with the class. */
    public ArrayList<String> attributes = new ArrayList<>();

    /** List of methods associated with the class. */
    public ArrayList<String> methods = new ArrayList<>();

    /** List of marks associated with each method. */
    public ArrayList<String> methodMarks = new ArrayList<>();

    /**
     * Constructs a SpecificationClass object with the given class name.
     *
     * @param className The name of the class.
     */
    public SpecificationClass(String className) {
        this.className = className;
    }

    /**
     * Adds a new attribute to the class.
     *
     * @param attribute The attribute to be added.
     */
    public void addAttribute(String attribute) {
        attributes.add(attribute);
    }

    /**
     * Adds a new method to the class.
     *
     * @param method The method to be added.
     */
    public void addMethod(String method) {
        methods.add(method);
    }

    /**
     * Adds a mark associated with a method.
     *
     * @param mark The mark to be added.
     */
    public void addMethodMark(String mark) {
        methodMarks.add(mark);
    }

    /**
     * Removes a specified attribute from the class.
     *
     * @param attribute The attribute to be removed.
     * @return True if the removal is successful, false otherwise.
     */
    public boolean removeAttribute(String attribute) {
        if (attributes.contains(attribute)) {
            attributes.remove(attribute);
            return true;
        }
        return false;
    }

    /**
     * Removes a specified method from the class.
     *
     * @param method The method to be removed.
     * @return True if the removal is successful, false otherwise.
     */
    public boolean removeMethod(String method) {
        if (methods.contains(method)) {
            methods.remove(method);
            return true;
        }
        return false;
    }

    /**
     * Returns the number of attributes associated with the class.
     *
     * @return The number of attributes.
     */
    public int numAttributes() {
        return attributes.size();
    }

    /**
     * Returns the number of methods associated with the class.
     *
     * @return The number of methods.
     */
    public int numMethods() {
        return methods.size();
    }

    /**
     * Returns the number of marks associated with the methods of the class.
     *
     * @return The number of marks.
     */
    public int numMarks() {
        return methodMarks.size();
    }

    /**
     * Sets the list of attributes for the class.
     *
     * @param listOfAttributes The list of attributes to be set.
     */
    public void getAttributes(ArrayList<String> listOfAttributes) {
        this.attributes = listOfAttributes;
    }

    /**
     * Sets the list of methods for the class.
     *
     * @param listOfMethods The list of methods to be set.
     */
    public void getMethods(ArrayList<String> listOfMethods) {
        this.methods = listOfMethods;
    }

    /**
     * Sets the list of marks for the methods of the class.
     *
     * @param listOfMarks The list of marks to be set.
     */
    public void getMethodMarks(ArrayList<String> listOfMarks) {
        this.methodMarks = listOfMarks;
    }

    /**
     * Prints the attributes of the class.
     */
    public void printAttributes() {
        for (String attribute : attributes) {
            System.out.println(attribute);
        }
    }

    /**
     * Prints the methods of the class.
     */
    public void printMethods() {
        for (String method : methods) {
            System.out.println(method);
        }
    }

    /**
     * Prints the marks associated with the methods of the class.
     */
    public void printMarks() {
        for (String mark : methodMarks) {
            System.out.println(mark);
        }
    }

    /**
     * Gets all attributes associated with the class.
     *
     * @return The list of attributes.
     */
    @Override
    public ArrayList<String> getAllAttributes() {
        return this.attributes;
    }

    /**
     * Gets all methods associated with the class.
     *
     * @return The list of methods.
     */
    @Override
    public ArrayList<String> getAllMethods() {
        return this.methods;
    }

    /**
     * Gets all marks associated with the methods of the class.
     *
     * @return The list of marks.
     */
    @Override
    public ArrayList<String> getAllMarks() {
        return this.methodMarks;
    }

    /**
     * Gets the name of the class.
     *
     * @return The name of the class.
     */
    @Override
    public String getClassName() {
        return className;
    }

    /**
     * Sets the name of the class.
     *
     * @param name The name to be set.
     */
    public void setClassName(String name) {
        this.className = name;
    }
}
