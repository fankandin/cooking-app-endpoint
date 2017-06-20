# Cooking recipes REST CRUD API

## How to lunch the application

1. Launch the DB MySQL container.
2. Compile the application if there are changes:

3.
```bash
java -jar ./app/target/cooking-app-[version].jar --spring.config.location=/path/to/db.properties --spring.profiles.active=production
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

## TO DO
1. Cover all endpoints with mockMvc tests.
2. Cover all services with DB-based tests.
3. Add KeyCloak authorization (needs frontend support)
4. Add pagination (needs frontend support)
5. Add search (needs frontend support)
6. Add Gitlab-CI (needs dockerization?)
7. Deploy