package com.example.springbootunitintergrationtest2.repository;

import com.example.springbootunitintergrationtest2.model.UserClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserClass, Long> {
    long deleteByUsername(String name);
    UserClass findByUserAdd(String add);
}
