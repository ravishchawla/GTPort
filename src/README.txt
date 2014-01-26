Application Developed by Ravish Chawla

This project consists of multiple subpanels built contained in a frame. Each panel has different operations that are executed when the panel is loaded.

The Jar File 'cs4400_Group32_GTPORT_v1.6.jar' can be executed to view the application. A valid connection to the Online Database is required, however. 



The structure of this program is as follows:

Classes: 
AddCourse, AdminHomepage, AdminReport, CourseSelection, FacultyHomepage, FacultyPersonalInformation, FacultyReport, FacultyServices, GradeAssignment, Homepage, Login, Registration, RegistrationComplete, RegistrationView, SelectDepartment, StudentHomepage, StudentPersonalInformation, StuentReport, StudentServices, TutorAssignment, TutorLogbook, and TutorSearch extend the class: 
GuiDesign.java

Class Connector connects to the MySQL database by using the JDBC connector

Class GTPort manages the entire program. It builds the connection, and instantiates/sets different pages in a cardlayout on the main frame. It also calls specific functions based on which panel is currently on top. 
