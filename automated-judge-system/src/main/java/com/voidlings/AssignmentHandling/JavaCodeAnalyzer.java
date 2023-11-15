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

        public Map<String, MethodInfo> getMethodInfoMap() {
            return methodInfoMap;
        }

        public void addAttributeName(String attributeName) {
            this.attributeNames.add(attributeName);
        }

        public void addMethodName(String methodName, String returnType, List<ParameterInfo> parameters) {
            MethodInfo methodInfo = new MethodInfo(methodName, returnType, parameters);
            this.methodInfoMap.put(methodName, methodInfo);
        }

        static class MethodInfo {
            private String methodName;
            private String returnType;
            private List<ParameterInfo> parameters;

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

        static class ParameterInfo {
            private String paramName;
            private String paramType;

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

    private static Map<String, JavaClassInfo> classInfoMap = new HashMap<>();

    static class NamingConventionVisitor extends VoidVisitorAdapter<Void> {

        @Override
        public void visit(ClassOrInterfaceDeclaration n, Void arg) {
            String className = n.getNameAsString();
            JavaClassInfo classInfo = new JavaClassInfo(className);
            classInfoMap.put(className, classInfo);

            super.visit(n, arg);
        }

        @Override
        public void visit(FieldDeclaration n, Void arg) {
            String attributeName = n.getVariable(0).getNameAsString();
            JavaClassInfo currentClassInfo = classInfoMap.get(n.findAncestor(ClassOrInterfaceDeclaration.class).get().getNameAsString());
            if (currentClassInfo != null) {
                currentClassInfo.addAttributeName(attributeName);
            }
            super.visit(n, arg);
        }

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
        String extractedFilesDirectory = "automated-judge-system/src/main/java/com/voidlings/Submissions";
        analyzeCode(extractedFilesDirectory);

        // Run App before running this.

        // These will be used to make the various assignmentClass objects.
        // Note that as some information to make these objects weren't extracted (e.g. method string, attribute modifier) either the AssignmentAttribute and AssignmentMethod classes will have to change, or this extracting method will have to be changed.
        List<AssignmentAttribute> attributes = new ArrayList<>();
        List<AssignmentMethod> methods = new ArrayList<>();

        // Print information about classes, attributes, and methods
        for (JavaClassInfo classInfo : classInfoMap.values()) {
            System.out.println("Class: " + classInfo.getClassName());

            System.out.println("Attributes: " + classInfo.getAttributeNames());
            // Format attributes and populate List.
            for (String attr : classInfo.getAttributeNames()){
                // Unfortunately this extraction method does not give the modifier, type and variableType.
                AssignmentAttribute newAttribute = new AssignmentAttribute(attr, "", "private", "");
                attributes.add(newAttribute);
            }

            // Print information about methods.
            for (JavaClassInfo.MethodInfo methodInfo : classInfo.getMethodInfoMap().values()) {
                System.out.println("Method: " + methodInfo.getMethodName());
                System.out.println("  Return Type: " + methodInfo.getReturnType());
                // Problem, this method of extracting method data does not include the output and the method string itself which is used in the creation of an AssignmentMethod variable.

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
