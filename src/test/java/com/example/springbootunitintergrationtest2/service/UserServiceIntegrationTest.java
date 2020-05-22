package com.example.springbootunitintergrationtest2.service;

import com.example.springbootunitintergrationtest2.model.UserClass;
import com.example.springbootunitintergrationtest2.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Transactional
class UserServiceIntegrationTest {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;


    @Test
    public void testFindAll() {

        List<UserClass> userClassList =
                userRepository.findAll();
        List<UserClass> aUserClassList =
                userService.findAll();
        assertEquals(aUserClassList, userClassList);
        assertThat(aUserClassList.size()).isEqualTo(4);
    }

    @Test
    public void testFindById() {

        UserClass aUserClass =
                userService.findById(2L);
        assertThat(aUserClass.getUserNo()).isEqualTo(2);
        assertThat(aUserClass.getUsername()).isEqualTo("Sachin");
        assertThat(aUserClass.getUserAdd()).isEqualTo("India1");

    }

    @Test
    public void testDeleteByName() {

        long recordDeleted =
                userService.deleteByName("Nilesh");
        assertThat(recordDeleted).isEqualTo(3L);
        assertThat(userService.findAll().size()).isEqualTo(1L);
    }

    @Test
    public void testCreateUSer() {
        UserClass expectedUser = new UserClass(1, "testN", "testAdd", 10);
        UserClass aUserClass =
                userService.createUser(expectedUser);

        assertThat(aUserClass.toString()).isEqualTo(expectedUser.toString());
        assertThat(userService.findAll().size()).isEqualTo(5L);
    }

    @Test
    public void testPutByAdd() {
        UserClass expectedUser = UserClass.builder()
                .username("testN")
                .userAdd("testAdd")
                .age(10)
                .build();
        UserService userService1 = new UserService(userRepository);
        UserClass aUserClass =
                userService1.putByAdd("India1", expectedUser);

        assertThat(aUserClass.getUserAdd()).isEqualTo(expectedUser.getUserAdd());
        assertThat(aUserClass.getUsername()).isEqualTo(expectedUser.getUsername());
        assertThat(aUserClass.getAge()).isEqualTo(expectedUser.getAge());
        assertThat(userRepository.count()).isEqualTo(4L);
    }
}