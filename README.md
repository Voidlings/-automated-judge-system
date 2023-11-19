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

# * Major Requirements:

* Input Data handling
  
  * The System must have access to a PDF version of an Assignment rubric, this rubric should follow the table convention.
  * The System must have access to a directory or zero or more Zip files.
  * The System must be run in an environment with permissions that allow for the creation of directories. (May move this to another category)
  * The System must traverse the directory of student assignment submissions, these must be .zip files, and each submission should be extracted to an individual directory within the "Submissions" directory.

* Code Evaluation
  
  * The System must extract and store the specifications from the PDF Assignment rubric file.
  * The System must assess the correctness of the submitted Java code against the predefined assignment specifications. 

* Feedback Generation

  * A detailed report must be created for each assignment submission.
  * Each report should contain the score attained, and the test passed, and provide hints for improvements.


# Use Case

Actor: Lecturer or Teaching Assistant

Precondition:

* The Automated Grading System is installed and configured.
* A PDF version of the assignment rubric is available.
* A directory of student assignment submissions in ZIP format is 
available.
*The user has permission to create directories in the working directory.

* Flow of Events:

* The user selects the PDF assignment rubric file and the directory of student assignment submissions.
  
* The system extracts and stores the specifications from the PDF assignment rubric file.
  
* The system traverses the directory of student assignment submissions, extracts each submission to an individual directory within the "Submissions" directory, and evaluates the correctness of the submitted Java code against the predefined assignment specifications.
  
* For each assignment submission, the system generates a detailed report containing the score attained, the tests passed, and hints for improvements.
  
*The system saves the generated reports to a designated directory.

Postcondition:

Detailed PDF reports with feedback and scores are generated for each student assignment submission.
The lecturer or teaching assistant can review the reports and provide additional feedback to students as needed.

Alternate Flows:

*If the PDF assignment rubric file is not found, the system throws an error.
(I need to go through the code further to get more)







