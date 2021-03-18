package com.api.uber.controller;

import com.api.uber.model.User;
import com.api.uber.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RestController
@RequestMapping("/v1/user/**")
public class UserController {

    @Autowired
    UserService userService;

    private final Logger log = LoggerFactory.getLogger(this.getClass());


    @RequestMapping(value="/register", method = RequestMethod.POST.POST,  produces = "application/json")
    public ResponseEntity<Object> createUser(@RequestBody User user) {

        try{
            userService.saveUser(user);
            log.info("User created, UserID:" + user.getUserID());
            return new ResponseEntity<>(user, HttpStatus.CREATED);

        }catch (Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);

        }

    }
}
