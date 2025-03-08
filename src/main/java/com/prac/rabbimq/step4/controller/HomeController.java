package com.prac.rabbimq.step4.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/news")
    public String news(Model model) {
        model.addAttribute("message", "Welcome to RabbitMQ News Sample!");
        return "news";
    }
}