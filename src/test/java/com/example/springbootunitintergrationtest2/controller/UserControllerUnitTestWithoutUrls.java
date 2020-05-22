package com.example.springbootunitintergrationtest2.controller;

import com.example.springbootunitintergrationtest2.model.UserClass;
import com.example.springbootunitintergrationtest2.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Transactional
class UserControllerUnitTestWithoutUrls {

    private UserController userController;
    private UserService mockUserService;

    @BeforeEach
    public void setup() {
        mockUserService = mock(UserService.class);
        userController = new UserController(mockUserService);
    }

    @Test
    void contextLoads() {
        assertThat(userController).isNotNull();
    }

    @Test
    public void testRequestParam() {

        String actualRes = userController.testRequestParam("testId", "testName", "testAge");

        assertThat(actualRes).isEqualTo("Hi testId ,testName, && testAge");
    }

    @Test
    public void testGetData() {
        List<UserClass> expectedList =
                Arrays.asList();

        when(mockUserService.findAll()).thenReturn(expectedList);

        List<UserClass> actualList =
                userController.getData();

        verify(mockUserService, times(1)).findAll();
        verifyNoMoreInteractions(mockUserService);

        assertThat(actualList).isNotNull();
        assertThat(actualList.size()).isEqualTo(expectedList.size());
    }

    @Test
    public void testgetDataByUserNo() {
        UserClass expectedClass =
                UserClass.builder()
                        .age(10)
                        .userAdd("testAdd")
                        .username("testName")
                        .build();

        when(mockUserService.findById(anyLong())).thenReturn(expectedClass);

        UserClass actualUser =
                userController.getDataByUserNo("1");

        verify(mockUserService, times(1)).findById(anyLong());
        verifyNoMoreInteractions(mockUserService);

        assertThat(actualUser).isNotNull();
        assertThat(actualUser).isEqualTo(expectedClass);
    }

    @Test
    public void testCreateUser() {
        UserClass expectedClass =
                UserClass.builder()
                        .age(10)
                        .userAdd("testAdd")
                        .username("testName")
                        .build();

        when(mockUserService.createUser(any())).thenReturn(expectedClass);

        UserClass actualUser =
                userController.createUser(any());

        verify(mockUserService, times(1)).createUser(any());
        verifyNoMoreInteractions(mockUserService);

        assertThat(actualUser).isNotNull();
        assertThat(actualUser).isEqualTo(expectedClass);
    }

    @Test
    public void testPutUser() {
        UserClass expectedClass =
                UserClass.builder()
                        .age(10)
                        .userAdd("testAdd")
                        .username("testName")
                        .build();

        when(mockUserService.putByAdd(anyString(),any())).thenReturn(expectedClass);

        UserClass actualUser =
                userController.putUser(anyString(), any());

        verify(mockUserService, times(1)).putByAdd(anyString(),any());
        verifyNoMoreInteractions(mockUserService);

        assertThat(actualUser).isNotNull();
        assertThat(actualUser).isEqualTo(expectedClass);
    }

    @Test
    public void testDeleteUser() {
        long expectedRes = 0;
        when(mockUserService.deleteByName(anyString())).thenReturn(expectedRes);

        long actualRes =
                userController.deleteUser(anyString());

        verify(mockUserService, times(1)).deleteByName(anyString());
        verifyNoMoreInteractions(mockUserService);

        assertThat(actualRes).isNotNull();
        assertThat(actualRes).isEqualTo(expectedRes);
    }

}