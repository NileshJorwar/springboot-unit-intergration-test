package com.example.springbootunitintergrationtest2.controller;

import com.example.springbootunitintergrationtest2.model.UserClass;
import com.example.springbootunitintergrationtest2.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@RestController
public class UserController {

    private UserService userService;
    public UserController(UserService userService){
        this.userService=userService;
    }

    @GetMapping("/all")
    public List<UserClass> getData(){
        return userService.findAll();
    }

    @GetMapping("/all/{id}")
    public UserClass getDataByUserNo(@PathVariable String id){
        return userService.findById(Long.parseLong(id));
    }

    @PostMapping("/create")
    public UserClass createUser(@RequestBody UserClass user)
    {
        return userService.createUser(user);
    }


    @DeleteMapping("/delete/{name}")
    public long deleteUser(@PathVariable String name){

        return userService.deleteById(name);
    }

    @PutMapping("/put/{address}")
    public UserClass putUser(@PathVariable String address, @RequestBody UserClass userClass){
        return userService.putByAdd(address,userClass);
    }
}
