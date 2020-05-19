## Enabling H2 console and its properties in Real Environment ##
spring.datasource.url= jdbc:h2:mem:testdb
spring.datasource.driver-class-name= org.h2.Driver
spring.datasource.username= sa
spring.datasource.password=
spring.jpa.show-sql= true
spring.jpa.hibernate.ddl-auto= update
spring.h2.console.enabled=true
spring.h2.console.path= /h2