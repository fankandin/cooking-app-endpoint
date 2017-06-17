# Cooking recipes REST CRUD API

## Development

### How to lunch the application locally

1. Launch the DB MySQL container.
2. Compile the application if there are changes:

3.
```bash
#java -jar -Dspring.profiles.active=production -Dspring.datasource.driveClassName=mysql -Dspring.datasource.url="jdbc:mysql://192.168.99.100/palinfo_cooking" -Dspring.datasource.username=palinfo_cooking -Dspring.datasource.password= ./app/target/cooking-app-[version].jar
#java -jar -Dspring.profiles.active=production -Djdbc.url="jdbc:mysql://192.168.99.100/palinfo_cooking" -Djdbc.username=palinfo_cooking -Djdbc.password= ./app/target/cooking-app-[version].jar
java -jar -Dspring.profiles.active=production -Dserver.port=8800 -Djdbc.url="jdbc:mysql://192.168.99.100/palinfo_cooking?characterEncoding=UTF-8&autoReconnect=true&useSSL=false" -Djdbc.user=palinfo_cooking -Djdbc.pass= ./app/target/cooking-app-[version].jar -Dport=8800
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