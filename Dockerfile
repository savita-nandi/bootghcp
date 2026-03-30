# Start from Eclipse Temurin JRE 21 on Ubuntu Jammy
FROM eclipse-temurin:21-jre-jammy

# Set the working directory inside the container
WORKDIR /workdir/app

# Copy the application artifact (assumes the built jar will be placed in the project root under target/)
# If your artifact has a different name adjust the COPY/ENTRYPOINT accordingly.
COPY target/bootghcp.war bootghcp.war

# Expose the application port
EXPOSE 8080

# Run the Spring Boot application. Using WAR here because the project builds a WAR; if you produce a JAR,
# change to app.jar and the command to java -jar app.jar
ENTRYPOINT ["/bin/sh", "-c", "java -jar /workdir/app/bootghcp.war"]
