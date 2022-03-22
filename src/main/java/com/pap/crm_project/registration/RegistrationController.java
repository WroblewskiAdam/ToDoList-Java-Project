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
        boolean isNewUserRegistered = registrationService.register(request);
        if (isNewUserRegistered) {
            return "confirmation";
        } else {
            return "redirect:/registration/form?taken_email_error";
        }
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
        boolean isConfirmed = registrationService.confirmToken(token);
        if (isConfirmed) {
            return "login";
        } else {
            return "redirect:/registration/form?confirmation_error";
        }
    }
}