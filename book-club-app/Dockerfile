FROM maven:3.6.3-jdk-11

ENV PROJECT_DIR=/project
RUN mkdir -p $PROJECT_DIR
WORKDIR $PROJECT_DIR

ADD ./pom.xml $PROJECT_DIR
RUN mvn dependency:resolve

ADD ./src/ $PROJECT_DIR/src
RUN mvn install

FROM openjdk:11-jdk-slim

ENV PROJECT_DIR=/project
RUN mkdir -p $PROJECT_DIR
WORKDIR $PROJECT_DIR
COPY --from=0 $PROJECT_DIR/target/book* $PROJECT_DIR/

EXPOSE 8080

CMD ["java", "-jar", "/project/book-club-docker.jar" ]

