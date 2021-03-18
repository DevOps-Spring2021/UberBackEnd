package com.api.uber.controller;

import com.api.uber.model.User;
import com.api.uber.services.UserService;
import com.api.uber.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RestController
@RequestMapping("/v1/users/**")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    UserValidator validator;

    private final Logger log = LoggerFactory.getLogger(this.getClass());


    @RequestMapping(value="/signup", method = RequestMethod.POST,  produces = "application/json")
    public ResponseEntity<Object> createUser(@RequestBody User user, BindingResult result) {

        try{
            validator.validate(user, result);

            if (result.hasErrors()) {
                return new ResponseEntity<>( result.getFieldErrors(), HttpStatus.BAD_REQUEST);
            }else {
                userService.saveUser(user);
                log.info("User created, UserID:" + user.getUserID());
                return new ResponseEntity<>(user, HttpStatus.CREATED);
            }

        }catch (Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);

        }

    }

    @RequestMapping(value="/{id}", method = RequestMethod.GET,  produces = "application/json")
    public ResponseEntity<Object> getUserBYID(@PathVariable("id") Long id){
        User user =userService.userBYID(id);
        if(user!= null) {
            log.info("get user, UserID:"+user.getUserID());
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            log.info("User not found, UserID:"+id);
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value="/{id}", method = RequestMethod.PUT,  produces = "application/json")
    public  ResponseEntity<Object> updateUser(@RequestBody User user,@PathVariable("id") Long id) {
        try {
            User newUser = userService.updateUser(user,id);
            log.info("User updated, UserID:"+user.getUserID());
            return new ResponseEntity<>(newUser, HttpStatus.ACCEPTED);

        }catch (Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

}
