package Stratergy;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.type.Type;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.voidlings.FileHandling.Folder;


import java.util.Arrays;
import java.util.List;

public class JavaReader implements ReadingStrategy {

    @Override
    public Boolean checkFormat(String path) {
        // Check if the file has a .java extension
        return path.toLowerCase().endsWith(".java");
    }

    static class NamingConventionVisitor extends VoidVisitorAdapter<Void> {
        private List<String> predefinedClassNames = Arrays.asList("ClassA", "ClassB", "ClassC");
        private List<String> predefinedMethodNames = Arrays.asList("methodA", "methodB", "methodC");
        private List<String> predefinedAttributeNames = Arrays.asList("attributeA", "attributeB", "attributeC");

        @Override
        public void visit(ClassOrInterfaceDeclaration n, Void arg) {
            // Check if class name is in the predefined list
            String className = n.getNameAsString();
            if (!predefinedClassNames.contains(className)) {
                System.out.println("Class name is not predefined: " + className);
                // You can add more logic here, such as storing results, calculating scores, etc.
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
                // You can add more logic here, such as storing results, calculating scores, etc.
            }

            super.visit(n, arg);
        }

        @Override
        public void visit(MethodDeclaration n, Void arg) {
            // Check if method name is in the predefined list
            String methodName = n.getNameAsString();
            if (!predefinedMethodNames.contains(methodName)) {
                System.out.println("Method name is not predefined: " + methodName);
                // You can add more logic here, such as storing results, calculating scores, etc.
            }

            // Retrieve method return type
            Type returnType = n.getType();
            System.out.println("Return type of method " + methodName + ": " + returnType);

            // Retrieve method parameters
            n.getParameters().forEach(parameter -> {
                String paramName = parameter.getNameAsString();
                Type paramType = parameter.getType();
                System.out.println("Parameter in method " + methodName + ": " + paramType + " " + paramName);
                // You can add more logic here, such as storing results, calculating scores, etc.
            });

            super.visit(n, arg);
        }
    }




    @Override
    public Folder extractFiles(String filePath, String destinationPath) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'extractFiles'");
    }
}
