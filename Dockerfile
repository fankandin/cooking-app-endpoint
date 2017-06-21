FROM java:8u111-jdk-alpine
RUN addgroup -g 1000 app && adduser -D  -G app -s /bin/false -u 1000 app
ENV APPLICATION_NAME cooking.app.api
EXPOSE 8800
COPY app/target/cooking-app-api.jar /app/
RUN mkdir /app/configuration
USER app
CMD ["java", "-Xmx512m", "-Xss256k", "-jar", "/app/cooking-app-api.jar", "--spring.config.location=/app/configuration/db.properties", "--spring.profiles.active=production"]