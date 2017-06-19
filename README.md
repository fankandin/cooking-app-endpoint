# Cooking recipes REST CRUD API

## Development

### How to lunch the application locally

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

## TO DO
1. Cover all endpoints with mockMvc tests.
2. Cover all services with DB-based tests.
3. Add KeyCloak authorization (needs frontend support)
4. Add pagination (needs frontend support)
5. Add search (needs frontend support)
6. Remove (secure) the DB pass from the command line that executes the app
7. Add Swagger
8. Add Gitlab-CI (needs dockerization?)
9. Deploy