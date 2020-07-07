-- *** PROJECT TEAM 2 DELIVERABLE 4 ***
-- Adam Clifton (akclifto@asu.edu)
-- Anne Landrum (aelandru@asu.edu)
-- Ivan Fernandez (iafernan@asu.edu)
-- Robert Ibarra (rnibarra@asu.edu)

Please see the included Relational Model.jpeg with this submission for the Relation Model Diagram.  The model did not
change from Deliverable 3.

Before starting the program, run the below source script in sql server.  This will create and populate a database
called "VGS", select the database, show its tables, and describe each for easier navigation when testing.

	1 -  VGS_Team2_Init.sql

This can be executed from the mySql server command line client with the following:

    "source C://<path-to-file-location>/SER322 Project Team 2/sql/VGS_Team2_Init.sql;"

The program provided is already compiled. To launch application from the command line, below is a example of the format:

    java -cp lib/mysql-connector-java-8.0.20.jar;bin vgs.Application <url> <username> <password> com.mysql.cj.jdbc.Driver

    or a more in-depth example:

    java -cp lib/mysql-connector-java-8.0.20.jar;bin vgs.Application "jdbc:mysql://localhost/VGS?autoReconnect=true&useSSL=false" root root com.mysql.cj.jdbc.Driver

Video Presentation link:

	***


If you have any issues running the program, please let us know. Thank you.

Project Team 2
