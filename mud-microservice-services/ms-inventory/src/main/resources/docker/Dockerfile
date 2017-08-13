FROM java:8
VOLUME /data/mud-microservice
ADD ms-inventory-1.0-SNAPSHOT app.jar
RUN bash -c 'touch /app.jar'
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]