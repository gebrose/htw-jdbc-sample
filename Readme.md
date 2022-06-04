### HTW Berlin, IMI, Datenbanken, SS 2022

# Spring boot JDBC sample application

**1. Prerequisites**

You need a PostgreSQL installation with the example schema 'uni' and the example data.
The installation can be local, but you can also use the HTW lab database server.

**2. Configuration**

Edit the property `jdbc.connectionString` in file  `src\main\resources\application.properties`.
The database server, user name, and password must match your local PostgreSQL installation.

**3. Preparation**

Run the integration test `BueroRaumEntityTest` first.

**4.Starting the application in IntelliJ Idea**

Go to `JdbcSampleApplication`. Right-click on the class name and select "Run Application" or "Debug Application" 