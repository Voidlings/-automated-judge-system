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

public class JavaCodeAnalyzer {

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
                List<ParameterInfo> parameters = new ArrayList<>();
                n.getParameters().forEach(parameter -> {
                    String paramName = parameter.getNameAsString();
                    String paramType = parameter.getTypeAsString();
                    parameters.add(new ParameterInfo(paramName, paramType));
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

    public static ArrayList<JavaClassInfo> processSubmissionFiles(String extractedFilesDirectory) {
        analyzeCode(extractedFilesDirectory);
        ArrayList <JavaClassInfo> classes = new ArrayList<JavaClassInfo>();

        for (JavaClassInfo classInfo : classInfoMap.values()) {
            // Populate list to return, all classes plus info.
            // Optional: format in another object type before making final list.
            classes.add(classInfo);
            /*
            System.out.println("Class: " + classInfo.getClassName());

            System.out.println("Attributes: " + classInfo.getAttributeNames());
            for (String attr : classInfo.getAttributeNames()) {
                AssignmentAttribute newAttribute = new AssignmentAttribute(attr, "", "private", "");
            }

            for (JavaClassInfo.MethodInfo methodInfo : classInfo.getMethodInfoMap().values()) {
                System.out.println("Method: " + methodInfo.getMethodName());
                System.out.println("  Return Type: " + methodInfo.getReturnType());

                System.out.println("  Parameters:");
                for (JavaClassInfo.ParameterInfo parameterInfo : methodInfo.getParameters()) {
                    System.out.println("    " + parameterInfo.getParamType() + " " + parameterInfo.getParamName());
                }
            }

            System.out.println();
            */
        }
        return classes;
    }

    public static void main(String[] args) {
    String extractedFilesDirectory = "automated-judge-system/src/main/java/com/voidlings/Submissions";
    processSubmissionFiles(extractedFilesDirectory);
    }

}

