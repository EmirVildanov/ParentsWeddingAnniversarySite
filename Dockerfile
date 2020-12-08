FROM alpine:latest as builder
RUN apk update && apk upgrade
RUN apk add openjdk8
RUN apk add --update nodejs npm
RUN apk add yarn

WORKDIR ~/repo

COPY . .
RUN ./gradlew

FROM openjdk:8-jre-alpine

COPY --from=builder ~/repo/build/libs/example-0.0.1-all.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]