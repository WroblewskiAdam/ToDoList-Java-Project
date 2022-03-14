package com.pap.crm_project.controller;

import com.pap.crm_project.entity.AppUser;
import com.pap.crm_project.service.interfaces.AppUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
//@RequestMapping(path = "pap/crm")
public class AppUserController {

    private AppUserService appUserService;

    public AppUserController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @GetMapping("/users")
    public String listUsers(Model model) {
        model.addAttribute("users", appUserService.getAllAppUsers());
        return "users";
    }

    @GetMapping("/users/new")
    public String createUserForm(Model model) {
        AppUser appUser = new AppUser();
        model.addAttribute("user", appUser);
        return "create_user";
    }

    @PostMapping("/users")
    public String addUser(@ModelAttribute("student") AppUser appUser) {
        appUserService.saveAppUser(appUser);
        return "redirect:/users";
    }

    @GetMapping("/users/edit/{id}")
    public String editUserForm(@PathVariable Long id, Model model) {
        model.addAttribute("user", appUserService.getAppUserById(id));
        return "edit_user";
    }

    @PostMapping("/users/{id}")
    public String updateStudent(@PathVariable Long id,
                                @ModelAttribute("user") AppUser appUser,
                                Model model) {

        AppUser oldAppUser = appUserService.getAppUserById(id);
        oldAppUser.setId(id);
        oldAppUser.setFirstName(appUser.getFirstName());
        oldAppUser.setLastName(appUser.getLastName());
        oldAppUser.setEmail(appUser.getEmail());

        appUserService.saveAppUser(oldAppUser);
        return "redirect:/users";
    }

    @GetMapping("/users/{id}")
    public String deleteUser(@PathVariable Long id) {
        appUserService.deleteAppUser(id);
        return "redirect:/users";
    }
}
