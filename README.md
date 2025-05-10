**Project Overview**
We developed a database management system for a banking system that focuses on managing customer accounts, tracking transactions, and handling loan applications and payments. The system will allow customers to manage their accounts and help manage finances easily by accurately displaying and updating data according to user transactions.

**Setting up and running the project**
1. Install MySQL 8.0 and execute the create_schema.sql and initialize_data.sql scripts in src/main/sql. NOTE: the create_schema.sql DROP any existing databases named 'Bank'
2. Next, in Database.java, configure your environment variables (URL, user, password) to connect to the MySQL Database.
3. Make sure to copy any dependencies in the pom.xml file. When using IntelliJ IDEA, you can search and add that dependency within the IDE.
4. Run the application from IntelliJ IDEA.
5. Visit local http://localhost:[your own port number]/ to start the application.

**Dependencies and Required software**
Dependencies: 
MySQL 8.0 (Database), 
Spark Java Framework (Application API routing), 
Gson (Java object to JSON conversion)
