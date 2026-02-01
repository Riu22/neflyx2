package com.neflyx2.neflyx2.controller;

import com.neflyx2.neflyx2.model.entiti.*;
import com.neflyx2.neflyx2.service.admin_service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class admin_controller {

    @Autowired
    private admin_service admin_service;

    @GetMapping("/dashboard")
    public String dashboard() { return "dashboard"; }

    @GetMapping("/list/{entity}")
    public String list(@PathVariable String entity, @RequestParam(defaultValue = "0") int page, Model model) {
        var itemsPage = admin_service.findPageDTO(entity, page, 50);
        model.addAttribute("items", itemsPage.getContent());
        model.addAttribute("entityName", entity);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", itemsPage.getTotalPages());
        return "list-view";
    }

    @GetMapping("/edit/{entity}/{id}")
    public String edit(@PathVariable String entity, @PathVariable String id, Model model) {
        Object realId = id.matches("\\d+") ? Integer.parseInt(id) : id;

        Object item = "0".equals(id) ? admin_service.createEntityInstance(entity) : admin_service.findById(entity, realId);

        model.addAttribute("item", item);
        model.addAttribute("entityName", entity);

        if (entity.contains("movie") || entity.equals("person")) {
            model.addAttribute("movies", admin_service.findTop100("movie"));
            model.addAttribute("persons", admin_service.findTop100("person"));
            model.addAttribute("allGenres", admin_service.findAll("genre"));
            model.addAttribute("allCountries", admin_service.findAll("country"));
            model.addAttribute("departments", admin_service.findAll("department"));
        }
        return getTemplateForEntity(entity);
    }

    @PostMapping("/save/movie")
    public String saveMovie(@ModelAttribute("item") movie m) { return saveAndRedirect("movie", m); }

    @PostMapping("/save/person")
    public String savePerson(@ModelAttribute("item") person p) { return saveAndRedirect("person", p); }

    @PostMapping("/movie_cast/save")
    public String saveCast(@ModelAttribute("item") movie_cast c) { return saveAndRedirect("movie_cast", c); }

    @PostMapping("/movie_crew/save")
    public String saveCrew(@ModelAttribute("item") movie_crew c) { return saveAndRedirect("movie_crew", c); }

    @GetMapping("/delete/{entity}/{id}")
    public String delete(@PathVariable String entity, @PathVariable String id) {
        Object realId = id.matches("\\d+") ? Integer.parseInt(id) : id;
        admin_service.delete(entity, realId);
        return "redirect:/admin/list/" + entity;
    }

    private String saveAndRedirect(String entity, Object data) {
        admin_service.save(entity, data);
        return "redirect:/admin/list/" + entity;
    }

    private String getTemplateForEntity(String entity) {
        return switch (entity.toLowerCase()) {
            case "movie" -> "movie-form";
            case "genre" -> "genre-form";
            case "person" -> "person-form";
            case "country" -> "country-form";
            case "movie_cast" -> "movie-cast-form";
            case "movie_crew" -> "movie-crew-form";
            default -> "dashboard";
        };
    }
}