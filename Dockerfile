FROM gradle:8.7.0-jdk17
COPY ./ ./code
WORKDIR ./code
RUN gradle bootJar
RUN mv ./build/libs/AuthApp-0.0.1.jar /AuthApp-0.0.1.jar
EXPOSE 8080
CMD ["java", "-jar","/AuthApp-0.0.1.jar"]