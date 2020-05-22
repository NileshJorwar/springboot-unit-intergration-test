package com.example.springbootunitintergrationtest2.model;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Transactional
class UserClassTest {

    @Autowired
    UserClass userClass;

    @Test
    public void testConstructor(){
        userClass = new UserClass(1,"Nil","Add",10);
        assertThat(userClass).isNotNull();
        assertThat(userClass.getUserNo()).isEqualTo(1);
        assertThat(userClass.getUsername()).isEqualTo("Nil");
        assertThat(userClass.getUserAdd()).isEqualTo("Add");
        assertThat(userClass.getAge()).isEqualTo(10);
    }

    @Test
    public void testSetters(){
        userClass = new UserClass();
        userClass.setUserNo(1);
        userClass.setUsername("Nil");
        userClass.setUserAdd("Add");
        userClass.setAge(10);

        assertThat(userClass).isNotNull();
        assertThat(userClass.getUserNo()).isEqualTo(1);
        assertThat(userClass.getUsername()).isEqualTo("Nil");
        assertThat(userClass.getUserAdd()).isEqualTo("Add");
        assertThat(userClass.getAge()).isEqualTo(10);
    }

    @Test
    public void testToString(){
        userClass = new UserClass(1,"testName","TestAdd",19);
        System.out.println(userClass);
        assertThat(userClass).isNotNull();
        assertThat(userClass.toString()).isEqualTo("UserClass(userNo=1, username=testName, userAdd=TestAdd, age=19)");
    }
}