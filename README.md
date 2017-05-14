# Cooking recipes REST CRUD API

## Development

### How to lunch the application locally

1. Launch the DB MySQL container.
2. Compile the application if there are changes:

3.
```bash
java -jar -Dspring.datasource.driveClassName=mysql -Dspring.datasource.url="jdbc:mysql://192.168.99.100/palinfo_cooking" -Dspring.datasource.username=palinfo_cooking -Dspring.datasource.password= ./app//target/cooking-app-[version].jar
```