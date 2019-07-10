FROM openjdk:8

LABEL name=java-rest-basic-skeleton
LABEL version=1.0.0

# Set the working directory to /app
WORKDIR /app

# copy any jar files from the first stage
COPY ./target/*.jar /app/java-rest-basic-skeleton.jar

EXPOSE 8080

CMD java -jar java-rest-basic-skeleton.jar -Dorg.slf4j.simpleLogger.defaultLogLevel=DEBUG
