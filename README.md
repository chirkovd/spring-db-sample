# spring-db-sample
Sample application that integrates Spring Boot + Hibernate + PostgreSQL/H2 + Flyway.
 Provided REST API allows user to load books predefined on application side and own books, added by API.
 There are 2 maven profiles: DEV - uses H2 and PROD - uses PostgreSQL database.

# How to start

```
   # run application with H2 database
   mvn spring-boot:run -P dev
   
   # run database instance by docker
   docker-compose up -d
   # run application with postrgeSQL
   mvn spring-boot:run -P prod
```

If application is started, then http://localhost:8080/books would return predefined list of books.

# Special details:
1) BooksService class contains init section, transaction is required for environment with several application instances. It should protect application from app books duplications.
2) Flyway uses migration scripts (resources/sql/migration) and update db scheme: there is intentional mistake in initialization postgres script, next scripts update broken table and create missed indexes.
3) JpaAuditing is quite simple, it just add creation and modification dates - would work fine with enabled SpringSecurity and use principals from security context