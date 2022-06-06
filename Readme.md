### HTW Berlin, IMI

#### Datenbanken, SS 2022, Brose

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

The project can also be run from the command line using `mvn org.springframework.boot:spring-boot-maven-plugin:run`

The running application can be accessed at `http://localhost:8080`.

**5. Extending the application**

The web application uses **Springboot**  for server-side rendering and **Thymeleaf** as a templating engine.
Thymeleaf has a powerful expression language,
but you do not need to learn any details to work on the tasks here.
Copying and modifying the example HTML code should be straightforward.

HTML files reside in `src/main/resources/templates`,
styles are defined in `src/main/resources/static/main.css`.