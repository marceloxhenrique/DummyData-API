FROM ubuntu:latest

RUN apt-get update \
    && apt-get install -y openjdk-17-jdk maven

WORKDIR /app

COPY . .

RUN mvn clean install

RUN ls target

EXPOSE 8080

CMD ["java", "-jar", "target/dummydata-0.0.1-SNAPSHOT.jar"]
