package com.pap.crm_project.registration;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/registration")
@AllArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping()
    public String register(@ModelAttribute("request") RegistrationRequest request) {
        registrationService.register(request);
        return "confirmation";
    }

    @GetMapping("/form")
    public String signUpNewUser(Model model) {
        RegistrationRequest request = new RegistrationRequest();
        model.addAttribute("request", request);
        return "registration";
    }

    @GetMapping("/confirmation")
    public String confirmation() {
        return "confirmation";
    }

    @GetMapping(path = "confirm")
    public String confirm(@RequestParam("token") String token) {
        registrationService.confirmToken(token);
        return "login";
    }
}