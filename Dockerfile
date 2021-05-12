FROM openjdk:11-jre
ARG JAR_FILE=target/propostas-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} proposta.jar
ENTRYPOINT ["java","-jar","/proposta.jar"]
EXPOSE 8080