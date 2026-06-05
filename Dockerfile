FROM eclipse-temurin:23-jre

WORKDIR /app

COPY applications/app-service/build/libs/ms-franchise.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","app.jar"]