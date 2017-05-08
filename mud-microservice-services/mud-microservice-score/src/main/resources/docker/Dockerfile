FROM java:8
VOLUME /data/mud-microservice
ADD mud-microservice-score-1.0-SNAPSHOT.jar app.jar
RUN bash -c 'touch /app.jar'
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]