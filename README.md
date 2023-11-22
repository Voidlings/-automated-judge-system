# -automated-judge-system
Project-COMP3607

# -Introduction 
Team Members:
* Dominique David
* Jasmine Dottin
* Adrian Frection
* Sarah Ghansam
* Eysah Ali
* Joshua Phillip

Project Scope:

This system will simplify the evaluation process by accepting zipped Java program submissions, assessing code correctness against assignment specifications, and generating detailed PDF reports with feedback and scores. It will be implemented entirely in Java and will adhere to best practices, incorporate design patterns, follow SOLID principles, and feature a comprehensive test suite to enhance the assignment evaluation process.

Project Purpose:

The Automated Grading System aims to assist Lecturers and Teaching Assistants in the grading process, freeing up valuable time for lesson planning. This improvement in efficiency contributes to the overall quality of education provided to students.

Outcome: Enhanced grading efficiency, allowing educators to focus on lesson planning and ultimately improving the quality of education for students.


# -Analysis

* #  Major Requirements:

* Input Data handling
  
  * The System must have access to a PDF version of an Assignment rubric, this rubric should follow the table convention.
  * The System must have access to a directory or zero or more Zip files.
  * The System must be run in an environment with permissions that allow for the creation of directories.
  * The System must traverse the directory of student assignment submissions, these must be .zip files, and each submission should be extracted to an individual directory within the "Submissions" directory.

* Code Evaluation
  
  * The System must extract and store the specifications from the PDF Assignment rubric file.
  * The System must assess the correctness of the submitted Java code against the predefined assignment specifications. 

* Feedback Generation

  * A detailed report must be created for each assignment submission.
  * Each report should contain the score attained, and the test passed, and provide hints for improvements.


# Use Case

# Actor: Lecturer or Teaching Assistant

* # Precondition:

* The Automated Grading System is installed and configured.
* A PDF version of the assignment rubric is available.
* A directory of student assignment submissions in ZIP format is 
available.
*The user has permission to create directories in the working directory.

* #  Flow of Events:

* The user selects the PDF assignment rubric file and the directory of student assignment submissions.
  
* The system extracts and stores the specifications from the PDF assignment rubric file.
  
* The system traverses the directory of student assignment submissions, extracts each submission to an individual directory within the "Submissions" directory, and evaluates the correctness of the submitted Java code against the predefined assignment specifications.
  
* For each assignment submission, the system generates a detailed report containing the score attained, the tests passed, and hints for improvements.
  
*The system saves the generated reports to a designated directory.

 # Postcondition:

Detailed PDF reports with feedback and scores are generated for each student assignment submission.
The lecturer or teaching assistant can review the reports and provide additional feedback to students as needed.

 # Alternate Flows:

* If the PDF assignment rubric file is not found, the system throws an error.
* If no ZIP files are found, the system alerts the user as such.

 # Target Students:

The target audience is students in Java programming courses. The system aims to provide a fair and automated assessment of their Java assignments, offering valuable feedback and grading based on specified criteria.

# Design


* # Design Patterns used:


 # Composite Design Pattern
 * Purpose: The composite design pattern was used to treat the Folder and JavaFile classes uniformly. They both implemented the FileComponent interface, this interface contains four abstract methods, getname, setName, isFolder, and getPath. This Design Pattern was implemented in a attempt to compose objects of similar nature into a hierachal system and then use them as though they were indiviual/ ungrouped components.
  
 * # Strategy Design Pattern:
 * Purpose: The Strategy Design Pattern was used to give the system options in relation to file conversion.The two strategies for conversion would be the implementation of the two concrete strategy classes JavatoText and PDFtoTexT, both these classes implement the FileConverter interface. The implementation of this code can be seen in the JavaHandler class and the SpecificationHandler class.


# Conformance to SOLID:
* # Single Responsibility Principle:
 *Each class is designed with a single responsibility.
  Examples: The ZIPFileHandler class is solely responsible for the extracting of composite objects referred to as files or folders from a compressed version of a folder.


 * # Open/Closed Principle:
 In the applications implementation Open/ Closed principle was used in multiple instance in an attempt to design Classes and Methods to facilitate new functionality without editing existing source code. This was done through the use of interfaces that allow the implemented concrete classes to facility modified functionality.
 
 * # Liskov Substitution Principle:
All SubTypes are replaceable by the their base classes. For Example Specification Components can make reference to all of the functionalities of its subtype specification class.


 * # Interface Segregation Principle:
 Class within the application a not forced to implement interfaces in which is not 100% necessary for the functionality of the concrete class.
 
 * # Dependency Inversion Principle:
 Concrete Classes that implement a high level module (Abstract Classes, Interfaces, etc) are not directly instantiated with there static and dynamic types being that of the low level class. PolyMorphism is used throughout the application where the static type is that of the high level module and the dynamic type is that of the low level module for all possible instances of Instantions and object references. 


### Implementation
- **How to Run:**
  - To run the project, follow these steps:
    1. Install Java Development Kit (JDK).
    2. Choose and install a Java IDE (e.g., IntelliJ IDEA, Eclipse, or Visual Studio Code).
    3. Download and set up the Spire.PDF library.
    4. Clone or download the project code from the GitHub repository.
    5. Obtain a valid license key for the Spire.PDF library.
    6. Configure the project by updating the license key in the code.
    7. Run the `App` class with the main method.

- **Setup Requirements:**
  - The setup requires JDK, an IDE, the Spire.PDF library, and a valid license key. Additional instructions may be found in the project documentation.

### Testing and Evaluation
- **Test Cases and Suites:**
  - The project employs test cases for grading attributes (`AttributeTestCase`) and methods (`MethodTestCase`). These cases evaluate Java files against specifications, producing attribute and method evaluations.

- **Demo Video Link:**



# Class Level API Documentation


# Public Class ZIPFileHandler


    Instances of ZIPFileHandler objects can only be obtained through monomorphic instantiation of a ZIPFileHandler Object


    * Main Methods of The Class
    The main method for the ZIPFileHandler, is the extractFiles() function that takes two parameters 1. String zipFilePath which contains a reference to a zip composed of student assignment submissions  and 2. String destinationPath used to indicated where the contents of the previous parameter reference will be stored. This method opens a ZIP file referenced by parameter one and extract the necessary files, creates a folder structure and determines whether files are java files or not. If they are they are added to a nested folder and added to it. these nested folders and returned to an object that stores them.


    *Main Clients of the Class
     The sole client of the ZIPFileHandler class is the App.java class


   *How to use the class
   1.Instantiate a ZIPFileHandler object with the desired parameter contents


   2. Instantiate an object Folder object to hold the Folders of extracted submission files which are the results returned by the ZIPHandler object


   3. Using the ZipFileHandler object created call the 'extractFiles' function and assign the results to the folder object created






        allSubmissions = zipFileHandler.extractFiles(directoryPath, destinationPath);
       if ((allSubmissions != null) && (allSubmissions.getFileComponents() != null) && (allSubmissions.getFolderList() != null)) {
           System.out.println("Extraction successful.");
       } else {
           System.err.println("Extraction failed. Ensure that the zip contains folder submissions with .java files");
           return;
       }


# Public Class SpecificationHandler


Instances of SpecificationFileHandler objects can only be obtained through monomorphic instantion of  a SPecificationHandler Object


*Main Method
The main method of the specificationHandler class is the , readSpecification function, It utilizes the PDFtoText class converter to obtain the text content and then parses the content line by line. The function identifies keywords such as Class, Attribute, and Method to extract information about classes, attributes, and methods. It maintains separate ArrayLists for attributes, methods, and class names, while also distinguishing between class information and a mark scheme table. The extracted data is used to create instances of a SpecificationClass object AsgClass , representing assignment classes, and populates them with attributes and methods.The function then returns an ArrayList of SpecificationComponents.


*Main Client
The sole client of the SpecificationHandler class is the App.java class


*How to use this class
1. Instantiate an ArrayList of specificationComponents


2. Instantiate a SpecificationHandler Object with a reference to a pdf you wish to read ad the parameter


3. using the SpecificationHandler Object call the readSpecificationFile and assign its results to the ArrayList of specificationComponents


Sample code:


       SpecificationHandler specification = new SpecificationHandler("COMP2603  Assignment 1 Marking Scheme.pdf");
       ArrayList<SpecificationComponents> specificationClasses = specification.readSpecificationFile();




# Public Class JavaHandler




# Public Interface Specification Component




# Public Class MethodTestCase
The purpose of this class is to generate a grade for the method implementations of the student’s code based on the assignment rubric.

The class has a public constructor and can be instantiated from its sole client, the App.java class, using the following example code:

MethodTestCase gradeMethods = new MethodTestCase(specificationArrayList, assigmentClassesArrayList);

To use the class functionality, the following example code can be used:

ArrayList<MethodEval> methodEval = gradeMethods.getMethodEvals();





# Public Class AttributeTestCase
The purpose of this class is to generate a grade for the attribute implementations of the student’s code based on the assignment rubric.

The class has a public constructor and can be instantiated from its sole client, the App.java class, using the following example code:

AttributeTestCase gradeAttr = new AttributeTestCase(specificationClasses, javas);

To use the class functionality, the following example code can be used:

ArrayList<AttributeEval> attrEval = gradeAttr.getAttributeEvals();
  
 
  





