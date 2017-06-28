# Cooking recipes REST CRUD API

## How to launch the application

### Native run
```bash
java -jar ./app/target/cooking-app-[version].jar --spring.config.location=/path/to/db.properties --spring.profiles.active=production
```
### Docker
```bash
docker create --name cooking-api-endpoint --link mysql -p 8800:8800 vintagedreamer/cooking-api-endpoint:latest
docker cp /path/to/db.properties cooking-api-endpoint:/app/configuration/db.properties 
```

4. db.properties sample:
```
db.driverClassName=com.mysql.jdbc.Driver
db.url=jdbc:mysql://localhost:3306/db_name?characterEncoding=UTF-8&autoReconnect=true&useSSL=false
db.username=db_user
db.password=db_pass
persistence.hibernate.dialect=org.hibernate.dialect.MySQL5Dialect
```

## Swagger
The app contains built-in [Swagger](http://swagger.io/) - a very handy tool for testing and documenting the API.
 * Swagger UI: <http://localhost:8800/swagger-ui.html>
 * Swagger 2.0 API documentation: <http://localhost:8800/v2/api-docs>
