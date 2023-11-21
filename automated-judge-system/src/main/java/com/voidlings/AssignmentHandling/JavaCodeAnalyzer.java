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
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * The JavaCodeAnalyzer class is responsible for analyzing Java code within a specified directory
 * and extracting information about classes, attributes, and methods. It uses the JavaParser library
 * to parse Java files and then employs a visitor pattern to traverse the Abstract Syntax Tree (AST)
 * and gather relevant information.
 *
 * @author Voidlings
 * @version 1.0
 */


public class JavaCodeAnalyzer {

    static class JavaClassInfo {
        private String className;
        private List<String> attributeNames = new ArrayList<>();
        private Map<String, MethodInfo> methodInfoMap = new HashMap<>();

        public JavaClassInfo(String className) {
            this.className = className;
        }

        public String getClassName() {
            return className;
        }

        public List<String> getAttributeNames() {
            return attributeNames;
        }

        /**
         * Map of method information, where the key is the method name and the value is MethodInfo.
         */

        public Map<String, MethodInfo> getMethodInfoMap() {
            return methodInfoMap;
        }
        /**
         * Adds an attribute name to the list of attribute names.
         *
         * @param attributeName The name of the attribute to be added.
         */

        public void addAttributeName(String attributeName) {
            this.attributeNames.add(attributeName);
        }
        /**
         * Adds method information to the map of method information.
         *
         * @param methodName   The name of the method.
         * @param returnType   The return type of the method.
         * @param parameters   The list of method parameters.
         */

        public void addMethodName(String methodName, String returnType, List<ParameterInfo> parameters) {
            MethodInfo methodInfo = new MethodInfo(methodName, returnType, parameters);
            this.methodInfoMap.put(methodName, methodInfo);
        }

        /**
         * Represents information about a method in a Java class, including its name, return type,
         * and a list of parameters.
         */

        static class MethodInfo {
            private String methodName;
            private String returnType;
            private List<ParameterInfo> parameters;

            /**
             * Constructs a MethodInfo object with the given method details.
             *
             * @param methodName The name of the method.
             * @param returnType The return type of the method.
             * @param parameters The list of method parameters.
             */

            public MethodInfo(String methodName, String returnType, List<ParameterInfo> parameters) {
                this.methodName = methodName;
                this.returnType = returnType;
                this.parameters = parameters;
            }

            public String getMethodName() {
                return methodName;
            }

            public String getReturnType() {
                return returnType;
            }

            public List<ParameterInfo> getParameters() {
                return parameters;
            }
        }
        /**
         * Represents information about a method parameter, including its name and type.
         */

        static class ParameterInfo {
            private String paramName;
            private String paramType;

            /**
             * Constructs a ParameterInfo object with the given parameter details.
             *
             * @param paramName The name of the parameter.
             * @param paramType The type of the parameter.
             */

            public ParameterInfo(String paramName, String paramType) {
                this.paramName = paramName;
                this.paramType = paramType;
            }

            public String getParamName() {
                return paramName;
            }

            public String getParamType() {
                return paramType;
            }
        }
    }
    /**
     * The map to store information about each Java class in the analyzed code.
     */

    private static Map<String, JavaClassInfo> classInfoMap = new HashMap<>();
   
    /**
     * A visitor class to traverse the AST and extract information based on naming conventions.
     * Implements the VoidVisitorAdapter for convenient AST traversal.
     */
  
     static class NamingConventionVisitor extends VoidVisitorAdapter<Void> {
  
        /**
         * Visits a class or interface declaration and extracts information about the class.
         *
         * @param n   The ClassOrInterfaceDeclaration node in the AST.
         * @param arg Unused argument.
         */
 
         @Override
        public void visit(ClassOrInterfaceDeclaration n, Void arg) {
            String className = n.getNameAsString();
            JavaClassInfo classInfo = new JavaClassInfo(className);
            classInfoMap.put(className, classInfo);

            super.visit(n, arg);
        }

        /**
         * Visits a field declaration and extracts information about the attribute.
         *
         * @param n   The FieldDeclaration node in the AST.
         * @param arg Unused argument.
         */

        @Override
        public void visit(FieldDeclaration n, Void arg) {
            String attributeName = n.getVariable(0).getNameAsString();
            JavaClassInfo currentClassInfo = classInfoMap.get(n.findAncestor(ClassOrInterfaceDeclaration.class).get().getNameAsString());
            if (currentClassInfo != null) {
                currentClassInfo.addAttributeName(attributeName);
            }
            super.visit(n, arg);
        }
        /**
         * Visits a method declaration and extracts information about the method.
         *
         * @param n   The MethodDeclaration node in the AST.
         * @param arg Unused argument.
         */

        @Override
        public void visit(MethodDeclaration n, Void arg) {
            String methodName = n.getNameAsString();

                Type returnType = n.getType();
                List<JavaClassInfo.ParameterInfo> parameters = new ArrayList<>();
                n.getParameters().forEach(parameter -> {
                    String paramName = parameter.getNameAsString();
                    String paramType = parameter.getTypeAsString();
                    parameters.add(new JavaClassInfo.ParameterInfo(paramName, paramType));
                });

                JavaClassInfo currentClassInfo = classInfoMap.get(n.findAncestor(ClassOrInterfaceDeclaration.class).get().getNameAsString());
                if (currentClassInfo != null) {
                    currentClassInfo.addMethodName(methodName, returnType.toString(), parameters);
                }
                
            super.visit(n, arg);
        }
    }

    /**
     * Analyzes Java code within the specified directory by walking through all Java files
     * and invoking the analyzeFile method for each file.
     *
     * @param directoryPath The path to the directory containing Java files.
     */

    public static void analyzeCode(String directoryPath) {
        try {
            Files.walk(Paths.get(directoryPath))
                    .filter(path -> path.toString().endsWith(".java"))
                    .forEach(JavaCodeAnalyzer::analyzeFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Analyzes a single Java file by parsing it using JavaParser and visiting the AST using
     * the NamingConventionVisitor to extract class, attribute, and method information.
     *
     * @param filePath The path to the Java file to be analyzed.
     */

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
        String extractedFilesDirectory = "automated-judge-system/src/main/java/com/voidlings/Submissions";
        analyzeCode(extractedFilesDirectory);

        // Print information about classes, attributes, and methods
        for (JavaClassInfo classInfo : classInfoMap.values()) {
            System.out.println("Class: " + classInfo.getClassName());
            System.out.println("Attributes: " + classInfo.getAttributeNames());

            // Print information about methods
            for (JavaClassInfo.MethodInfo methodInfo : classInfo.getMethodInfoMap().values()) {
                System.out.println("Method: " + methodInfo.getMethodName());
                System.out.println("  Return Type: " + methodInfo.getReturnType());

                // Print information about method parameters
                System.out.println("  Parameters:");
                for (JavaClassInfo.ParameterInfo parameterInfo : methodInfo.getParameters()) {
                    System.out.println("    " + parameterInfo.getParamType() + " " + parameterInfo.getParamName());
                }
            }

            System.out.println();
        }
    }
}
