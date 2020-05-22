package com.example.springbootunitintergrationtest2.controller;

import com.example.springbootunitintergrationtest2.SpringbootUnitIntergrationTest2Application;
import com.example.springbootunitintergrationtest2.model.UserClass;
import com.example.springbootunitintergrationtest2.service.UserService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@Import(UserControllerIntegrationTestRestTemplate.ControllerConfigP.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = SpringbootUnitIntergrationTest2Application.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Transactional
public class UserControllerIntegrationTestRestTemplate {


    @Autowired
    TestRestTemplate testRestTemplate;
    @LocalServerPort
    int randomPort;

    @Test
    public void testAllMethod() throws Exception {

        String id = "etstId", name = "testName", age = "10";
        String url =
                "http://localhost:" + randomPort + "/all/test/testId?name=testName&age=testAge";
        URI uri = new URI(url);
        String actaul =
                testRestTemplate.getForObject(uri, String.class);
        System.out.println(actaul);
        assertThat(actaul).isEqualTo("Hi testId ,testName, && testAge");

    }

    @Test

    public void testgetData() throws Exception {

        String url =
                "http://localhost:" + randomPort + "/all";
        URI uri = new URI(url);
        JsonNode jsonNode =
                testRestTemplate.getForObject(uri, JsonNode.class);
        List<UserClass> actual = new ObjectMapper().convertValue(jsonNode, new TypeReference<List<UserClass>>() {
        });

        System.out.println(actual.get(0).getAge());

        assertThat(actual).isNotNull();
        assertThat(actual.size()).isEqualTo(4);
        assertThat(actual.get(0).getAge()).isEqualTo(11);
        assertThat(actual.get(1).getAge()).isEqualTo(12);
        assertThat(actual.get(2).getAge()).isEqualTo(13);
        assertThat(actual.get(3).getAge()).isEqualTo(10);

    }

    @Test
    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public void testgetDataByUserNo() throws Exception {

        String url =
                "http://localhost:" + randomPort + "/all/2";
        URI uri = new URI(url);
        UserClass actual =
                testRestTemplate.getForObject(uri, UserClass.class);

        System.out.println(actual.toString());

        assertThat(actual).isNotNull();
        assertThat(actual.getAge()).isEqualTo(11);
        assertThat(actual.getUserAdd()).isEqualTo("India1");
        assertThat(actual.getUsername()).isEqualTo("Sachin");

    }

    @Test
    public void testCreateUser() throws Exception {

        String url =
                "http://localhost:" + randomPort + "/create";
        URI uri = new URI(url);
        UserClass expectedUser =
                UserClass.builder()
                        .age(16)
                        .userAdd("USA")
                        .username("Nil")
                        .build();

        UserClass actual =
                testRestTemplate.postForObject(uri, expectedUser, UserClass.class);

        System.out.println(actual.toString());

        assertThat(actual).isNotNull();
        assertThat(actual.getAge()).isEqualTo(16);
        assertThat(actual.getUserAdd()).isEqualTo("USA");
        assertThat(actual.getUsername()).isEqualTo("Nil");

    }

    @Test
    @Rollback(true)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void testPutUser() throws Exception {

        String url =
                "http://localhost:" + randomPort + "/put/India1";
        URI uri = new URI(url);
        UserClass expectedUser =
                UserClass.builder()
                        .age(16)
                        .username("Sachin")
                        .userAdd("USA")
                        .build();

        ResponseEntity<UserClass> actual =
                testRestTemplate.exchange(uri, HttpMethod.PUT, new HttpEntity<>(expectedUser), UserClass.class);

        System.out.println(actual.getBody());

        assertThat(actual).isNotNull();
        assertThat(actual.getBody().getUsername()).isEqualTo("Sachin");
        assertThat(actual.getBody().getUserAdd()).isEqualTo("USA");
        assertThat(actual.getBody().getAge()).isEqualTo(16);

    }

    @Test
    public void testDeleteUser() throws Exception {

        String url =
                "http://localhost:" + randomPort + "/delete/Nilesh";
        URI uri = new URI(url);

        ResponseEntity<Long> actual =
                testRestTemplate.exchange(uri, HttpMethod.DELETE, null, Long.class);

        System.out.println(actual.getStatusCode());

        assertThat(actual).isNotNull();

    }

    @TestConfiguration
    public static class ControllerConfigP {
        @Bean
        TestRestTemplate testRestTemplate() {
            return new TestRestTemplate();
        }
    }

}