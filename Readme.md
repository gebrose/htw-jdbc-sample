### HTW Berlin, IMI, Datenbanken, SS 2022

# Spring boot JDBC sample application

**1. Prerequisites**

You need a PostgreSQL installation with the latest example schema 'uni' (included here) and the example data.

The installation can be local, but you can also use the HTW lab database server.

The sample code requires a Java 17 SDK to compile and run.

**2. Configuration**

Edit the property `jdbc.connectionString` in file  `src\main\resources\application.properties`.

The database server, user name, and password must match the values for your local PostgreSQL installation.

**3. Preparation**

Run `mvn clean test` or the individual integration test `BueroRaumEntityTest` to test your environment setup.

**4.Starting the application in IntelliJ Idea**

Go to `de.htw_berlin.imi.db.JdbcSampleApplication`.
Right-click on the class name and select "Run Application" or "Debug Application" in IntelliJ Idea.

The application can be accessed at `http://localhost:8080`