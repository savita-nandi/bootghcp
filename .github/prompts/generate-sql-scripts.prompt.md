---
Description: 'Generate Sql scripts to create persistent database and tables'
agent: 'edit'
---

# Generate seperate Sql scripts to create persistent database and tables in H2 database
Create new table(s) by using the instructions detailed below.

## Requirements:

### Generate a Sql script to create a new persistent database 
- Database name: testghcp
- Database location: database\testghcp.mv.db

### Generate a Sql script to create new persistent table 
- Table name: userdetails
- Fieldname-datatype: id-bigint, firstname-string, lastname-string, emailid-string, createdon-datetime, updatedon-datetime
- File location: resources\schema.sql

### Generate a Sql script to insert 2 rows of sample records into table 
- Table name: userdetails
- File location: resources\data.sql

### Pom.xml changes
- Include latest version of library "com.h2database->h2" in pom.xml.
- Include the project-root `database` folder as resources so its files are placed
  into WEB-INF/classes and available on the application classpath in the WAR package


### application.properties changes
- Include H2 database connection property details in application.properties file.
- Ensure the database property details refers to a persistent database

### Comments for reference
- The actual process of executing the sql scripts is handled by H2 library when the application is run.