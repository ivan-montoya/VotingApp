# VotingApp

To run this application, the Eclipse IDE for Java development and MySQL must be installed on the local machine being used. 

Eclipse: https://www.eclipse.org/

MySql: https://www.mysql.com/

If both are installed, import this project into a workspace in Eclipse. Using the command line, use the file 'sqlfile.sql' to restore the database for the program. This will require that you already have a MySql database set up to be used to restore the application database. To allow the application to connect to your MySql database, go to the file named DatabaseObject.java (VotingApp/src/database/DatabaseObject.java) and make the necessary modifications in the first Try block, as indicated in the file.

Unless the application is set up correctly, an error message mentioning that the connection has failed will appear.

