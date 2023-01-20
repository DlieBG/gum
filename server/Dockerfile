FROM gradle:jdk17-alpine as build

WORKDIR /app

COPY build.gradle .
COPY settings.gradle .
COPY src src

RUN gradle build --no-daemon
RUN rm build/libs/*-plain.jar

FROM amazoncorretto:17-alpine

WORKDIR /app

COPY --from=build /app/build/libs/*.jar bene.jar

ENTRYPOINT ["java", "-jar", "bene.jar"]
