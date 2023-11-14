package com.voidlings.AssignmentHandling;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.type.Type;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JavaCodeAnalyzer {

    static class JavaClassInfo {
        private String className;
        private List<String> attributeNames = new ArrayList<>();
        private List<String> methodNames = new ArrayList<>();

        public JavaClassInfo(String className) {
            this.className = className;
        }

        public String getClassName() {
            return className;
        }

        public List<String> getAttributeNames() {
            return attributeNames;
        }

        public List<String> getMethodNames() {
            return methodNames;
        }

        public void addAttributeName(String attributeName) {
            this.attributeNames.add(attributeName);
        }

        public void addMethodName(String methodName) {
            this.methodNames.add(methodName);
        }
    }

    // Declare classInfoMap as a static field
    private static Map<String, JavaClassInfo> classInfoMap = new HashMap<>();

    static class NamingConventionVisitor extends VoidVisitorAdapter<Void> {
        private List<String> predefinedClassNames = Arrays.asList("a", "b", "ClassC");
        private List<String> predefinedMethodNames = Arrays.asList("methodA", "methodB", "methodC");
        private List<String> predefinedAttributeNames = Arrays.asList("attributeA", "attributeB", "attributeC");

        @Override
        public void visit(ClassOrInterfaceDeclaration n, Void arg) {
            // Check if class name is in the predefined list
            String className = n.getNameAsString();
            if (!predefinedClassNames.contains(className)) {
                System.out.println("Class name is not predefined: " + className);

                // Store information about the class
                JavaClassInfo classInfo = new JavaClassInfo(className);
                classInfoMap.put(className, classInfo);
            }

            // Visit field declarations (attributes)
            super.visit(n, arg);
        }

        @Override
        public void visit(FieldDeclaration n, Void arg) {
            // Check if attribute name is in the predefined list
            String attributeName = n.getVariable(0).getNameAsString();
            if (!predefinedAttributeNames.contains(attributeName)) {
                System.out.println("Attribute name is not predefined: " + attributeName);

                // Get the current class's info and add the attribute name
                JavaClassInfo currentClassInfo = classInfoMap.get(n.findAncestor(ClassOrInterfaceDeclaration.class).get().getNameAsString());
                if (currentClassInfo != null) {
                    currentClassInfo.addAttributeName(attributeName);
                }
            }

            super.visit(n, arg);
        }

        @Override
        public void visit(MethodDeclaration n, Void arg) {
            // Check if method name is in the predefined list
            String methodName = n.getNameAsString();
            if (!predefinedMethodNames.contains(methodName)) {
                System.out.println("Method name is not predefined: " + methodName);

                // Get the current class's info and add the method name
                JavaClassInfo currentClassInfo = classInfoMap.get(n.findAncestor(ClassOrInterfaceDeclaration.class).get().getNameAsString());
                if (currentClassInfo != null) {
                    currentClassInfo.addMethodName(methodName);
                }
            }

            // Retrieve method return type
            Type returnType = n.getType();
            System.out.println("Return type of method " + methodName + ": " + returnType);

            // Retrieve method parameters
            n.getParameters().forEach(parameter -> {
                String paramName = parameter.getNameAsString();
                Type paramType = parameter.getType();
                System.out.println("Parameter in method " + methodName + ": " + paramType + " " + paramName);
            });

            super.visit(n, arg);
        }
    }

    public static void analyzeCode(String directoryPath) {
        try {
            Files.walk(Paths.get(directoryPath))
                    .filter(path -> path.toString().endsWith(".java"))
                    .forEach(JavaCodeAnalyzer::analyzeFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void analyzeFile(Path filePath) {
        try {
            ParseResult<CompilationUnit> parseResult = new JavaParser().parse(filePath);

            if (parseResult.isSuccessful()) {
                CompilationUnit compilationUnit = parseResult.getResult().get();
                NamingConventionVisitor visitor = new NamingConventionVisitor();
                compilationUnit.accept(visitor, null);
            } else {
                System.err.println("Parsing failed for file: " + filePath);
                parseResult.getProblems().forEach(System.err::println);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (RuntimeException e) {
            System.err.println("Runtime exception during file processing: " + filePath);
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Exception during file processing: " + filePath);
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String extractedFilesDirectory = "automated-judge-system/src/main/java/com/voidlings/submissions";
        analyzeCode(extractedFilesDirectory);

        // Print information about classes
        for (JavaClassInfo classInfo : classInfoMap.values()) {
            System.out.println("Class: " + classInfo.getClassName());
            System.out.println("Attributes: " + classInfo.getAttributeNames());
            System.out.println("Methods: " + classInfo.getMethodNames());
            System.out.println();
        }
    }
}
