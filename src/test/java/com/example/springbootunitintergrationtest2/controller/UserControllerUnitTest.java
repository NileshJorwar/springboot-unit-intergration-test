package com.example.springbootunitintergrationtest2.controller;

import com.example.springbootunitintergrationtest2.model.UserClass;
import com.example.springbootunitintergrationtest2.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.usertype.UserCollectionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

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

    @Test
    public void testAllMethod() throws Exception {
        String id = "etstId", name = "testName", age = "10";
        mockMvc.perform(get("/all/test/{id}", id)
                .param("name", name)
                .param("age", age)

        ).andExpect(status().isOk())
                .andExpect(content().string(containsString("Hi etstId ,testName, && 10")));
    }

    @Test
    public void testGetDataMethod() throws Exception {
        List<UserClass> userClassList = Arrays.asList();
        when(mockUserService.findAll()).thenReturn(userClassList);
        MvcResult mvcResult = mockMvc.perform(
                get("/all")
        ).andExpect(status().isOk())
                .andReturn();

        verify(mockUserService, times(1)).findAll();
        verifyNoMoreInteractions(mockUserService);

        assertThat(mvcResult).isNotNull();
        assertThat(mvcResult.getResponse().getContentLength()).isEqualTo(userClassList.size());

    }

    @Test
    public void testCreate() throws Exception {
        UserClass expectedUserClass = new UserClass(1,"Nil","snis",10);

        when(mockUserService.createUser(any())).thenReturn(expectedUserClass);
        MvcResult mvcResult = mockMvc.perform(
                post("/create")
                .content(new ObjectMapper().writeValueAsString(expectedUserClass))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andReturn();

        verify(mockUserService, times(1)).createUser(any());
        verifyNoMoreInteractions(mockUserService);

        assertThat(mvcResult).isNotNull();
        assertThat(mvcResult.getResponse().getContentAsString()).isEqualTo(new ObjectMapper().writeValueAsString(expectedUserClass));

    }
}