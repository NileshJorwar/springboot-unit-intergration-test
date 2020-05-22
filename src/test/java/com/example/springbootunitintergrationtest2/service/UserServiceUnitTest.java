package com.example.springbootunitintergrationtest2.service;

import com.example.springbootunitintergrationtest2.model.UserClass;
import com.example.springbootunitintergrationtest2.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Transactional
class UserServiceUnitTest {

    UserService userService;
    UserRepository mockUserRepository;

    @BeforeEach
    public void setup() {
        mockUserRepository = mock(UserRepository.class);
        userService = new UserService(mockUserRepository);
    }

    @Test
    public void testFindAll() {
        List<UserClass> expectedUserClassList =
                new ArrayList<>();
        expectedUserClassList.add(new UserClass(1, "Name", "Add", 10));
        expectedUserClassList.add(new UserClass(1, "Name", "Add", 10));
        expectedUserClassList.add(new UserClass(1, "Name", "Add", 10));
        expectedUserClassList.add(new UserClass(1, "Name", "Add", 10));
        expectedUserClassList.add(new UserClass(1, "Name", "Add", 10));

        when(mockUserRepository.findAll()).thenReturn(expectedUserClassList);

        List<UserClass> actualUserClassList = userService.findAll();

        verify(mockUserRepository, times(1)).findAll();
        verifyNoMoreInteractions(mockUserRepository);

        assertThat(actualUserClassList).isNotNull();
        assertThat(actualUserClassList).isEqualTo(expectedUserClassList);
        assertThat(actualUserClassList.size()).isEqualTo(expectedUserClassList.size());

    }

    @Test
    public void testputByAdd_should_return_null() {
        UserClass expectedUserClass = new UserClass(1, "Name", "Add", 10);

        when(mockUserRepository.findByUserAdd(anyString())).thenReturn(new UserClass());

        UserClass actualUserClass = userService.putByAdd(anyString(),expectedUserClass);

        verify(mockUserRepository, times(1)).findByUserAdd(anyString());
        verifyNoMoreInteractions(mockUserRepository);

        assertThat(actualUserClass).isNull();

    }

    @Test
    public void testputByAdd_should_return_userClass() {
        UserClass expectedUserClass = new UserClass(1, "Name", "Add", 10);

        when(mockUserRepository.findByUserAdd(anyString())).thenReturn(new UserClass(1,"Sachin","Add",13));
        when(mockUserRepository.save(any())).thenReturn(expectedUserClass);
        UserClass actualUserClass = userService.putByAdd(anyString(),expectedUserClass);

        verify(mockUserRepository, times(1)).findByUserAdd(anyString());
        verify(mockUserRepository,times(1)).save(any());
        verifyNoMoreInteractions(mockUserRepository);

        assertThat(actualUserClass).isNotNull();

    }
}