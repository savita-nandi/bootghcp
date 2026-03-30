---
Description: 'Generate a Bat fie  to build a docker container image'
agent: 'edit'
---

# Generate Dockerfile


## Requirements:

### Generate Dockerfile
- Create a new Docker file that is optimized for Springboot 3.x.x application.
- The file should be located in project root folder that uses the JDK image "eclipse-temurin:21-jre-jammy". 
- Add a line to set the working directory in the container to be "Workdir/testghcp". 
- Expose the port "8080" for the containerized app.
- Check if you need to include multi-stage build comamnds and commands for faster build of springBoot App.



### Comments for reference
- Not at this time

- For Windows 11 OS, Create a new bat file in project root folder as "deployapp.bat". The file should include commands to start Tomcat Server 10 located in folder "C:\Savitha\Projects\SERVERS\apache-tomcat-10.0.11\bin". It should then Undeploy bootghcp.war if present in folder "C:\Savitha\Projects\SERVERS\apache-tomcat-10.0.11\webapps". Then it should deploy latest bootghcp.war from project target folder to folder "C:\Savitha\Projects\SERVERS\apache-tomcat-10.0.11\webapps".