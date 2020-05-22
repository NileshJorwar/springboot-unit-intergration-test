## Enabling H2 console and its properties in Real Environment ##
```
spring.datasource.url= jdbc:h2:mem:testdb\n
spring.datasource.driver-class-name= org.h2.Driver
spring.datasource.username= sa
spring.datasource.password=
spring.jpa.show-sql= true
spring.jpa.hibernate.ddl-auto= update
spring.h2.console.enabled=true
spring.h2.console.path= /h2
```
# Notes #

## Ways to Define the properties in Tests ##
@SpringBootTest(properties = {"spring.profiles.active=test"})
@ActiveProfiles(value = {"test"})
@TestPropertySource("/application-test.properties")


## Unit Test for Controller using WebMvcTest annotation ##
```
Apart from these mentioned differences in framework, 
one major difference is @RequestParam will always expect a value to bind. 
Hence, if value is not passed, it will give error. This is not the case in @QueryParam
To explicitly give the option, use required = false while using @RequestParam
```
@GetMapping("/all/test/{id}")
//@GetMapping("/all/test/{id}?name=nil&age=10") Not needed in RequestParam
Queryparam not used, instead use RequestParam for query parameters in Springboot

@WebMvcTest Includes both the @AutoConfigureWebMvc and the @AutoConfigureMockMvc, among other functionality.Includes both the @AutoConfigureWebMvc and the @AutoConfigureMockMvc, among other functionality.
@WebMvcTest does not start any server. Since the web server is not started,  RestTemplate or TestRestTemplate cannot be used with @WebMvcTest environment.

If you want to use RestTemplate or TestRestTemplate, then you need to start a server with @SpringBootTest (using webEnviroment attribute).
```
Very Imp - when using @WebMvcTest
make sure to add @Autowired over MockMvc class and @MockBean over
dependent class while writing test for controller
No need to setup standalone stuff for mockmvc (needed when using 
SpringBootTest)
```    

- Add @Builder to Entity class for use in (ClassName obj = ClassName.builder().prop1().prop2().build())
- Make sure to add MediaTYpe while dealing with PUT/POST in tests (unit test).

## Unit test using only method signature for controller ##
- Make sure to perform initial setup of controller and its dependencies
  using Mockito in @BeforeEach
- Call the Controller methods while mocking service methods 

When writing Tests for methods -
    Make sure to 
        - Write return type of actual method as expected output of
          method such as List, String.
        Example.
        ```
                List<UserClass> expectedList =
                        Arrays.asList();
      
                when(mockUserService.findAll()).thenReturn(expectedList);
        
                List<UserClass> actualList =
                        userController.getData();
        
                verify(mockUserService,times(1)).findAll();
                verifyNoMoreInteractions(mockUserService);
        
                assertThat(actualList).isNotNull();
                assertThat(actualList.size()).isEqualTo(expectedList.size());
        ```
## Integration Tests for Controller using SpringBootTest annotation along with MockMvc ##
- Similar to the unit tests written using WebMvcTest annotation
- Except it bootstraps the entire application
- Keep all code of methods similar to the unit tests written using WebMvcTest annotation 
- Remove @WebMvcTest and replace it with @SpringBootTest
- Dont autowire the MockMvc class, no need to use @MockBean over dependent class (service class)
- Controller is needed now (not needed in Unit Tests since we were testing URLS)
- Perform the SetUp using @BeforeEach as below

```
    @SpringBootTest(classes = SpringbootUnitIntergrationTest2Application.class)
    public class UserControllerIntegrationTestSpringBootTest {
    
        MockMvc mockMvc;
        UserController mockUserController;
        UserService mockUserService;
    
        @BeforeEach
        public void setup() {
            mockUserService = mock(UserService.class);
            mockUserController = new UserController(mockUserService);
            this.mockMvc = standaloneSetup(this.mockUserController).build();
        }

```
Earlier in Unit Test using @WebMvcTest

```
@WebMvcTest
public class UserControllerUnitTest {

    @Autowired
    MockMvc mockMvc;
    //Controller not needed
    //UserController mockUserController;

    @MockBean
    UserService mockUserService;

    //Below method not needed when using @WebMvcTest
    @BeforeEach
    public void setup() {
        //mockUserService = mock(UserService.class);
        //mockUserController = new UserController(mockUserService);
        //this.mockMvc = standaloneSetup(this.mockUserController).build();
    }

```

## Integration Tests for Controller using SpringBootTest annotation along with RestTemplate ##
- Make sure to add datasource (database) properties in application.properties
- Make sure to add randomport in SpringBootTest annotation webenvironment and autowire it within the class
```
@Import(UserControllerIntegrationTestRestTemplate.ControllerConfigP.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = SpringbootUnitIntergrationTest2Application.class)
public class UserControllerIntegrationTestRestTemplate {


    @Autowired
    TestRestTemplate testRestTemplate;
    @LocalServerPort
    int randomPort;
}
```
- Add TestRestTemplate in test class configuration and import (@Import(UserControllerIntegrationTestRestTemplate.ControllerConfigP.class)) it within.
```
@TestConfiguration
    public static class ControllerConfigP {
        @Bean
        TestRestTemplate testRestTemplate() {
            return new TestRestTemplate();
        }
    }
```
- For REST endpoints(GET/POST) to work with TestREstTemplate, should include database properties
  along with creation of schema and insertion of records in schema.sql and data.sql files. 
```
schema.sql
drop table if exists user_class;
create table user_class
(
user_no int auto_increment primary key,
username varchar(255) null,
user_add varchar(255) null,
age int null
 )
-------------
data.sql
insert into user_class (age, user_add, username, user_no) values (10, 'India', 'Nilesh', 1);
insert into user_class (age, user_add, username, user_no) values (11, 'India1', 'Nilesh', 2);
insert into user_class (age, user_add, username, user_no) values (12, 'India2', 'Nilesh', 3);
insert into user_class (age, user_add, username, user_no) values (13, 'India3', 'Nilesh', 4);
```
When Error comes in TestRestTemplate "java.lang.ClassCastException: java.util.LinkedHashMap cannot be cast to com.example.springbootunitintergrationtest2.model.UserClass"
    Get the list in JsonNode and then convert to the list
   ```      
            JsonNode jsonNode =
                    testRestTemplate.getForObject(uri, JsonNode.class);
            List<UserClass> actual = new ObjectMapper().convertValue(jsonNode, new TypeReference<List<UserClass>>() {
            });

   ```
- Make sure to use Exchange Method of TestRestTemplate for PUT/DELETE http methods
  with first parameter being uri, httpmethod, input (in case of PUT), return type class
## Use WebTestClient for webflux ##
----------Add Later
## Issues 
- TestRestTemplate --- method testgetDataByUserNo() failed as it picks up the data commited by other method which it should not
- when ran seperate tests, runs but when as suite failed