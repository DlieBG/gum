FROM maven:3.8.7-amazoncorretto-17 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn verify --fail-never
COPY src src
RUN sed -i 's/debug=true//' src/main/resources/application.properties
RUN sed -i 's/.*logging.level.root.*//' src/main/resources/application.properties
RUN echo "logging.level.root=off" >> src/main/resources/application.properties
RUN mvn -f pom.xml package -DskipTests -Dmaven.test.skip=true

FROM debian:11
RUN apt-get update && apt install openjdk-17-jre-headless vim nano -y
COPY --from=build /app/target/cli-*-SNAPSHOT.jar /usr/local/gum/gum.jar
RUN echo "#!/usr/bin/env bash" > /bin/gum
RUN echo "java -jar /usr/local/gum/gum.jar \"\$@\"" >> /bin/gum
RUN chmod +x /bin/gum
# mkdir test && cd test && echo "tester" | gum init http://localhost:8080/api/test
