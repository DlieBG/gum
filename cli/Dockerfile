FROM maven:3.8.7-amazoncorretto-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src src
RUN mvn -f pom.xml package -DskipTests -Dmaven.test.skip=true

FROM debian:11
RUN apt-get update && apt install openjdk-17-jre-headless vim nano -y
COPY --from=build /app/target/cli-*-SNAPSHOT.jar /usr/local/gum/gum.jar
RUN echo "#!/usr/bin/env bash" > /bin/gum
RUN echo "java -jar /usr/local/gum/gum.jar \"\$@\"" >> /bin/gum
RUN chmod +x /bin/gum
# RUN mkdir repo && cd repo && gum init https://gum.asdf.de/api/repo
