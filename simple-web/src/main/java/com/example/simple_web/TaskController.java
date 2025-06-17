package com.example.simple_web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class TaskController {

    private final List<Task> tasks = new ArrayList<>();

    @GetMapping("/")
    private String index(Model model) {
        model.addAttribute("tasks", tasks);
        return "index";
    }

}
