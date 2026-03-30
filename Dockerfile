# --- Stage 1: Build the application WAR file ---
# Start from Eclipse Temurin JRE 21 on Ubuntu Jammy
FROM eclipse-temurin:21-jre-jammy
# Set the working directory inside the container
WORKDIR /myapp
COPY . .
# Build the application
RUN mvn clean package -DskipTests

# Stage 2: Deploy the WAR file to a Tomcat container
# Ensure the Tomcat version supports the JDK version used for building
FROM tomcat:10.1.24-jdk21-temurin-jammy

# Remove default web applications (optional, adjust as needed for your deployment)
RUN rm -rf /usr/local/tomcat/webapps/*

# Set the JAVA_OPTS environment variable to pass JVM arguments
# Tomcat's catalina.sh script automatically picks up the JAVA_OPTS and CATALINA_OPTS variables
ENV JAVA_OPTS="-Dspring.profiles.active=prod"

# Create the custom directory for the database file
RUN mkdir -p /opt/myapp/db/

# Set the working directory in the container
WORKDIR /usr/local/tomcat/webapps/
# Copy the WAR file from the 'build' stage to the Tomcat webapps directory
COPY --from=build /myapp/target/*.war bootghcp.war 

# Copy the local database file (e.g., 'testghcp.mv.db' from your host machine's build context) 
# to the newly created directory inside the container
COPY --from=build /myapp/target/bootghcp/WEB-INF/classes/Database/H2/testghcp.mv.db /opt/myapp/db/testghcp.mv.db
COPY --from=build /myapp/target/bootghcp/WEB-INF/classes/Database/H2/testghcp.trace.db /opt/myapp/db/testghcp.trace.db

EXPOSE 8080

# The default CMD of the tomcat image runs catalina.sh, which starts the server
CMD ["catalina.sh", "run"]
