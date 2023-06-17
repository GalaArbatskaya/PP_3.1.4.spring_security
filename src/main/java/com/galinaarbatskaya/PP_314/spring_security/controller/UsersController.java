package com.galinaarbatskaya.PP_314.spring_security.controller;

import com.galinaarbatskaya.PP_314.spring_security.models.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/user")
public class UsersController {

     @GetMapping()
        public String showUserInfo(Model model, @AuthenticationPrincipal User user) {
            model.addAttribute("user", user);
            return "myUser";
        }

}
