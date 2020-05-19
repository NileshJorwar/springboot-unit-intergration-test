package com.example.springbootunitintergrationtest2.controller;

import com.example.springbootunitintergrationtest2.model.UserClass;
import com.example.springbootunitintergrationtest2.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.awt.*;
import java.util.List;

@RestController
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /*
    * Apart from these mentioned differences in framework, one major difference is @RequestParam will always expect a value to bind. Hence, if value is not passed, it will give error. This is not the case in @QueryParam
    To explicitly give the option, use required = false while using @RequestParam*/
    @GetMapping("/all/test/{id}")
    //@GetMapping("/all/test/{id}?name=nil&age=10") Not needed in RequestParam
    public String testRequestParam(@PathVariable String id, @RequestParam("name") String name, @RequestParam("age") String age) {
        return "Hi " + id + " ," + name + ", && " + age;
    }


    @GetMapping("/all")
    public List<UserClass> getData() {
        return userService.findAll();
    }

    @GetMapping("/all/{id}")
    public UserClass getDataByUserNo(@PathVariable String id) {
        return userService.findById(Long.parseLong(id));
    }

    @PostMapping(value = "/create",consumes = "application/json")
    public UserClass createUser(@RequestBody UserClass user) {
        return userService.createUser(user);
    }


    @DeleteMapping("/delete/{name}")
    public long deleteUser(@PathVariable String name) {

        return userService.deleteById(name);
    }

    @PutMapping("/put/{address}")
    public UserClass putUser(@PathVariable String address, @RequestBody UserClass userClass) {
        return userService.putByAdd(address, userClass);
    }
}
