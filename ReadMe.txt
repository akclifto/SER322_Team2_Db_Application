-- *** PROJECT TEAM 2 DELIVERABLE 4 ***
-- Adam Clifton (akclifto@asu.edu)
-- Anne Landrum (aelandru@asu.edu)
-- Ivan Fernandez (iafernan@asu.edu)
-- Robert Ibarra (rnibarra@asu.edu)

Please see the included Deliverable 3-Relational Model.jpeg with this submission for the Relation Model Diagram.

Before starting the program, run the below source script in sql server.  This will create and populate a database
called "VGS", select the database, show its tables, and describe each for easier navigation when testing.

	1 -  VGS_Team2_Init.sql

This can be execute from the mySql server client command line with the following:
    "source C://<path-to-file-location>/VGS_Team2_Init.sql;"

To launch application from command line can be done similar to the following example:

    java -cp lib/mysql-connector-java-8.0.20.jar;bin vgs.Application "jdbc:mysql://localhost/VGS?autoReconnect=true&useSSL=false" <username> <password> com.mysql.cj.jdbc.Driver


Video Presentation link: 
	***


