package com.pap.crm_project;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TemplateController {

    @GetMapping("/start")
    public String start() {
        return "start";
    }

    @GetMapping("/login")
    public String loginUser() { return "login"; }

    @GetMapping("/home")
    public String home() { return "home"; }

    @GetMapping("/main")
    public String main() { return "main"; }
}
