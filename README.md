# Cooking recipes REST CRUD API

## How to launch the application

### Out-of-box Cooking App
Use this [Cooking App Containerized](https://github.com/fankandin/cooking-app-containerized) repository to run a fully-functional demo in 1 click.

### Native run
First build the app with maven:
```
mvn package
```
Then launch the jar artifact:
```bash
java -jar ./app/target/cooking-app-api-[version].jar --spring.config.location=/path/to/db.properties --spring.profiles.active=production
```

### Docker
```bash
docker create --name cooking-api-endpoint --link mysql -p 8800:8800 vintagedreamer/cooking-api-endpoint:latest
docker cp /path/to/db.properties cooking-api-endpoint:/app/configuration/db.properties
docker start cooking-api-endpoint
```

## Configuration
env.properties sample:
```
db.driverClassName=com.mysql.jdbc.Driver
hibernate.dialect=org.hibernate.dialect.MySQL5Dialect
db.url=jdbc:mysql://dbhost:3306/cooking?characterEncoding=UTF-8&autoReconnect=true&useSSL=false
db.username=db_user
db.password=db_pass
spring.cors.origins=*
hibernate.hbm2ddl.auto=validate
```

## Swagger
The app contains built-in [Swagger](http://swagger.io/) - a very handy tool for testing and documenting the API.
 * Swagger UI: <http://localhost:8800/swagger-ui.html>
 * Swagger 2.0 API documentation: <http://localhost:8800/v2/api-docs>
