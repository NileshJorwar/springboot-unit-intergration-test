package com.example.springbootunitintergrationtest2.service;

import com.example.springbootunitintergrationtest2.model.UserClass;
import com.example.springbootunitintergrationtest2.repository.UserRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserClass> findAll() {
        return userRepository.findAll();
    }

    public UserClass findById(Long userNo) {
        return userRepository.findById(userNo).get();
    }

    @Transactional
    public long deleteByName(String name) {
        return userRepository.deleteByUsername(name);
    }

    public UserClass putByAdd(String add, UserClass userClass) {
        UserClass resUser = userRepository.findByUserAdd(add);
        UserClass result=null;
        if(resUser.getAge()!=0)
        {
            resUser.setAge(userClass.getAge());
            resUser.setUserAdd(userClass.getUserAdd());
            resUser.setUsername(userClass.getUsername());
            //resUser.setUserNo(userClass.getUserNo());
            result= userRepository.save(resUser);
        }
        return result;
    }

    public UserClass createUser(UserClass userClass) {
        UserClass userClass1 = userRepository.save(userClass);
        return userClass1;
    }
}
