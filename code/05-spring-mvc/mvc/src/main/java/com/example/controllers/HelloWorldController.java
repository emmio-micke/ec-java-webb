package com.example.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HelloWorldController {
    List<String> users = new ArrayList<>();

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String sayHello(Model model) {
        model.addAttribute("greeting", "Hello World from Spring 5 MVC");

        return "welcome";
    }

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public String printName(Model model, @ModelAttribute("username") String username) {
        users.add(username);
        model.addAttribute("users", users);
        return "users";
    }
}
