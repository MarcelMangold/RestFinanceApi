# RestFinanceApi



##application.properties##
```
spring.jpa.hibernate.ddl-auto=none

spring.datasource.initialization-mode=always
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL5InnoDBDialect
spring.datasource.platform=org.hibernate.dialect.MySQL5InnoDBDialect
spring.jpa.show-sql=true
spring.datasource.url=jdbc:mysql://<url>:<port>/<databasename>
spring.datasource.username=<user>
spring.datasource.password=<password>

spring.jpa.properties.hibernate.jdbc.lob.non_contextual_creation=true

server.port=9000
server.error.include-stacktrace=always
spring.thymeleaf.enabled=true

```

