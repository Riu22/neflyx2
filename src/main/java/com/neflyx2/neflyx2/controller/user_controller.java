package com.neflyx2.neflyx2.controller;

import com.neflyx2.neflyx2.service.user_service;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class user_controller {
    @Autowired
    user_service user_service;

    @GetMapping("/neflyx2/login")
    public String login() {
        return "login";
    }
    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, Model model, HttpSession session) {
        boolean success = user_service.login(username, password);
        if (success){
            session.setAttribute("username", username);
            return "redirect:/";
        }
        model.addAttribute("error", "Credenciales inválidas");
        return "login";
    }

    @GetMapping("/neflyx2/register")
    public String register() {
        return "register";
    }
    @PostMapping("/register")
    public String register(@RequestParam String username, @RequestParam String password, @RequestParam String email, Model model) {
        user_service.register(username, password, email);
            return "login";
    }
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
