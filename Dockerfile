FROM gradle:8.3.0-jdk21-alpine as builder
USER root
WORKDIR /builder
ADD . /builder
RUN gradle build

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
EXPOSE 8080
COPY --from=builder /builder/build/libs/spring-kotlin-ddd-0.0.1.jar .
CMD ["java", "-jar", "spring-kotlin-ddd-0.0.1.jar"]