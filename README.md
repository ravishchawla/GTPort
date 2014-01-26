GTPort
======

A Database Application, written in Java, that uses the MySQL Database System. 

Objectives 
----------
 - To practice writing queries and transactions in SQL
 - To practice implementing a server-based Database and connect it using a JDBC connector. 
 - To practice making a Graphical Application using the Java Swing Graphical User Interface
 - To practice Object Orientated Concepts, such as inheritence and polymorphism
 

About the Project
-----------------
The project was part of a Course Assignment for CS 4400, Database Systems. The goal of the project was to create a MYSQL Database, so that data could be exported from and imported into the database using a network connector. As a interface for the user, a Graphical application was created, that allowed the user to go through different frames and find the information needed. 

Because of the extent of the project, inheritence was used to simplify programming. However, a different class was needed for each frame. Most of the frames extend a bsic class design, called GuiDesign.java. GuiDesign.java contains the basic layout that is used for each frame. 

The important functionality of the project is contained in the classes GTPort.java and Connector.java. GTPort.java manages all the panels, and Connector.java connects to the database. Other classes, such as FacultyReport.java aggregate information from the database using SQL Queries and Transactions to inflate the information on a panel. 

The executable file is 'cs4400_Group32_GTPORT_v1.6.jar'. However, because the database is no longer held in Georgia Tech's servers, the application will not run properly. 

Application developed by Ravish Chawla.

