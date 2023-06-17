package com.galinaarbatskaya.PP_314.spring_security.controller;

import com.galinaarbatskaya.PP_314.spring_security.models.User;
import com.galinaarbatskaya.PP_314.spring_security.service.UserService;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String users(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("user", user);
        model.addAttribute("allRoles", userService.getAllRoles());
        model.addAttribute("allUsers", userService.getAllUsers());

        return "admin";
    }

    @PostMapping
    public String create(@ModelAttribute("user") @Valid User user) {

        userService.save(user);
        return "redirect:/admin";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("user") @Valid User user, @PathVariable("id") long id) {
        userService.update(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") long id) {
        userService.removeUserById(id);
        return "redirect:/admin";
    }

}

