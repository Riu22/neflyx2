package com.neflyx2.neflyx2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class error_controller {
    @GetMapping("/error-acceso")
    public String accessError(@RequestParam(value = "mensaje", required = false) String mensaje, Model model) {
        model.addAttribute("errorMsg", mensaje != null ? mensaje : "No tienes permiso para ver esto.");
        return "error_page";
    }
}
