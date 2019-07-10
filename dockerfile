FROM maven:3.5-jdk-8 as build

# Set the working directory to /app
WORKDIR /app

# Copy all the application code into the image
COPY . /app

RUN mvn -Dmaven.test.skip=true clean package

FROM tomcat:8.5.30-jre8
COPY --from=build /app/target/*.war /usr/local/tomcat/webapps/
