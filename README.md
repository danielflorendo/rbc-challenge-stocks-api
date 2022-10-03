#Usage
Navigate to the root folder of the project and create the jar file:<br>

```
./mvnw clean package
```

Navigate to `rbc-challenge-stocks-api/target` and execute the jar file using: <br>

```
java -jar rbc-challenge-stocks-api-0.0.1-SNAPSHOT.jar
```

Then, navigate to the swagger UI using your browser at: <br>

```
http://localhost:8080/swagger-ui/index.html
```

#Testing notes
The program creates a MySQL database and all tables are dropped at program shutdown.<br>
You can change the shutdown behaviour in the `application.properties` file under `src/main/resources` by modifying the `spring.jpa.hibernate.ddl-auto` property.
