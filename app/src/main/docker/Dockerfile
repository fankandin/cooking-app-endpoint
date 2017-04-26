FROM java:8u111-jdk-alpine
RUN addgroup -g 1000 app && adduser -D  -G app -s /bin/false -u 1000 app
ENV APPLICATION_NAME api.datasource.cooking
EXPOSE 8080
COPY maven/service.jar /app/
USER app
CMD ["java", "-Xmx512m", "-Xss256k", "-jar", "/app/service.jar"]