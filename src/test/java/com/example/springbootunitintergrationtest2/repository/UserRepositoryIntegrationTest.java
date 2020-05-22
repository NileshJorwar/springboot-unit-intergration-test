package com.example.springbootunitintergrationtest2.repository;

import com.example.springbootunitintergrationtest2.model.UserClass;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Transactional
class UserRepositoryIntegrationTest {

    @Autowired
    UserRepository userRepository;

    @Test
    @Transactional
    public void testdeleteByUsername() {
        userRepository.deleteByUsername("Nilesh");
        assertThat(userRepository.count()).isEqualTo(1);
    }

    @Test
    public void testfindByUserAdd() {
        UserClass actual =
                userRepository.findByUserAdd("India1");
        assertThat(actual).isNotNull();
        assertThat(actual.getUsername()).isEqualTo("Sachin");
    }

}