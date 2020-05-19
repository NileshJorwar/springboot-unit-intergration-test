## Enabling H2 console and its properties in Real Environment ##
spring.datasource.url= jdbc:h2:mem:testdb
spring.datasource.driver-class-name= org.h2.Driver
spring.datasource.username= sa
spring.datasource.password=
spring.jpa.show-sql= true
spring.jpa.hibernate.ddl-auto= update
spring.h2.console.enabled=true
spring.h2.console.path= /h2

## Notes ##
``
Apart from these mentioned differences in framework, one major difference is @RequestParam will always expect a value to bind. Hence, if value is not passed, it will give error. This is not the case in @QueryParam
To explicitly give the option, use required = false while using @RequestParam
``
@GetMapping("/all/test/{id}")
//@GetMapping("/all/test/{id}?name=nil&age=10") Not needed in RequestParam
Queryparam not used, instead use RequestParam for query parameters in Springboot

