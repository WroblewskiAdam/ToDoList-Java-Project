package com.pap.crm_project;

import com.pap.crm_project.entities.applicationuser.ApplicationUser;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TemplateController {

    @GetMapping("/start")
    public String start() { return "start"; }

    @GetMapping("/login")
    public String loginUser() { return "login"; }

    @GetMapping("/home")
    public String home() { return "home"; }

//    @GetMapping("/home")
//    public String home(@AuthenticationPrincipal ApplicationUser user, Model model) {
//        model.addAttribute("user", user);
//        return "home";
//    }
}

