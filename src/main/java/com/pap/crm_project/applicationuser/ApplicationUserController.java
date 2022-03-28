package com.pap.crm_project.applicationuser;

import com.pap.crm_project.email.EmailService;
import com.pap.crm_project.team.Team;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
public class ApplicationUserController {

    private ApplicationUserService applicationUserService;
//    private EmailService emailService;

    @GetMapping("/users/showAll")
    public String getAllUsers(Model model){
        List<ApplicationUser> users = applicationUserService.getAllApplicationUsers();
        model.addAttribute("users", users);
        return "users";
    }

    @DeleteMapping(path = "{userId}")
    public void deleteUser(@PathVariable("userId") Integer userId) {
        // TODO
    }

    @GetMapping("/edit/form")
    public String editUser( Model model) {
        ApplicationUser u = new ApplicationUser();
        model.addAttribute("user", u);
        return "edit_user";
    }

    @PostMapping("/edit")
    public String updateUser(@AuthenticationPrincipal ApplicationUser applicationUser, @ModelAttribute("user") ApplicationUser editedUser) {

        applicationUser.setFirstName(editedUser.getFirstName());
        applicationUser.setLastName(editedUser.getLastName());
        applicationUser.setImg(editedUser.getImg());

        applicationUserService.updateApplicationUser(applicationUser);
        return "/home";
    }

    // TODO dokończyć zmianę hasła
//    @GetMapping("/password_change")
//    public String changePassword(Model model) {
//        String email = "";
//        model.addAttribute("email", email);
//        return "email_form";
//    }
}
