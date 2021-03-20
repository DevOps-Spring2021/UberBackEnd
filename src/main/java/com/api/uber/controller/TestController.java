package com.api.uber.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class TestController {
    @RequestMapping(value = "/testHealth", method = RequestMethod.GET, produces = "application/json")
    public String getHealth() {
        return "Hello from Backend!!!";
    }
}
