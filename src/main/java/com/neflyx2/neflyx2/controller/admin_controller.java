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
    public String dashboard() {
        return "dashboard";
    }

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
        Object item;

        if ("0".equals(id)) {
            item = admin_service.createEntityInstance(entity);
        } else {
            Object realId;

            if (entity.equals("movie_crew")) {
                String[] parts = id.split("-");
                if (parts.length == 3) {
                    realId = new movie_crew_id(
                            Integer.parseInt(parts[0]),
                            Integer.parseInt(parts[1]),
                            Integer.parseInt(parts[2])
                    );
                } else {
                    realId = id;
                }
            } else if (entity.equals("movie_cast")) {
                String[] parts = id.split("-");
                if (parts.length == 3) {
                    realId = new movie_cast_id(
                            Integer.parseInt(parts[0]),
                            Integer.parseInt(parts[1]),
                            Integer.parseInt(parts[2])
                    );
                } else {
                    realId = id;
                }
            } else {
                realId = id.matches("\\d+") ? Integer.parseInt(id) : id;
            }

            item = admin_service.findById(entity, realId);
        }

        model.addAttribute("item", item);
        model.addAttribute("entityName", entity);

        loadReferenceData(entity, model);

        return getTemplateForEntity(entity);
    }

    @PostMapping("/save/movie")
    public String saveMovie(@ModelAttribute("item") movie m) {
        admin_service.save("movie", m);
        return "redirect:/admin/list/movie";
    }

    @PostMapping("/save/person")
    public String savePerson(@ModelAttribute("item") person p) {
        admin_service.save("person", p);
        return "redirect:/admin/list/person";
    }

    @PostMapping("/save/genre")
    public String saveGenre(@ModelAttribute("item") genre g) {
        admin_service.save("genre", g);
        return "redirect:/admin/list/genre";
    }

    @PostMapping("/save/country")
    public String saveCountry(@ModelAttribute("item") country c) {
        admin_service.save("country", c);
        return "redirect:/admin/list/country";
    }

    @PostMapping("/save/movie_cast")
    public String saveMovieCast(@ModelAttribute("item") movie_cast mc) {
        if (mc.getId() == null) {
            movie_cast_id id = new movie_cast_id(
                    mc.getMovie().getMovie_id(),
                    mc.getPerson().getPerson_id(),
                    mc.getGender() != null ? mc.getGender().getGender_id() : null
            );
            mc.setId(id);
        }
        admin_service.save("movie_cast", mc);
        return "redirect:/admin/list/movie_cast";
    }

    @PostMapping("/save/movie_crew")
    public String saveMovieCrew(@ModelAttribute("item") movie_crew mcr) {
        if (mcr.getId() == null) {
            movie_crew_id id = new movie_crew_id(
                    mcr.getMovie().getMovie_id(),
                    mcr.getPerson().getPerson_id(),
                    mcr.getDepartment() != null ? mcr.getDepartment().getDepartment_id() : null
            );
            mcr.setId(id);
        }
        admin_service.save("movie_crew", mcr);
        return "redirect:/admin/list/movie_crew";
    }

    @PostMapping("/save/keyword")
    public String saveKeyword(@ModelAttribute("item") keyword k) {
        admin_service.save("keyword", k);
        return "redirect:/admin/list/keyword";
    }

    @PostMapping("/save/department")
    public String saveDepartment(@ModelAttribute("item") department d) {
        admin_service.save("department", d);
        return "redirect:/admin/list/department";
    }

    @PostMapping("/save/language")
    public String saveLanguage(@ModelAttribute("item") language l) {
        admin_service.save("language", l);
        return "redirect:/admin/list/language";
    }

    @PostMapping("/save/language_role")
    public String saveLanguageRole(@ModelAttribute("item") language_role lr) {
        admin_service.save("language_role", lr);
        return "redirect:/admin/list/language_role";
    }

    @PostMapping("/save/gender")
    public String saveGender(@ModelAttribute("item") gender g) {
        admin_service.save("gender", g);
        return "redirect:/admin/list/gender";
    }

    @GetMapping("/delete/{entity}/{id}")
    public String delete(@PathVariable String entity, @PathVariable String id) {
        try {
            Object realId;

            if (entity.equals("movie_crew")) {
                String[] parts = id.split("-");
                if (parts.length == 3) {
                    realId = new movie_crew_id(
                            Integer.parseInt(parts[0]),
                            Integer.parseInt(parts[1]),
                            Integer.parseInt(parts[2])
                    );
                } else {
                    throw new IllegalArgumentException("ID de movie_crew inválido: " + id);
                }
            } else if (entity.equals("movie_cast")) {
                String[] parts = id.split("-");
                if (parts.length == 3) {
                    realId = new movie_cast_id(
                            Integer.parseInt(parts[0]),
                            Integer.parseInt(parts[1]),
                            Integer.parseInt(parts[2])
                    );
                } else {
                    throw new IllegalArgumentException("ID de movie_cast inválido: " + id);
                }
            } else {
                realId = id.matches("\\d+") ? Integer.parseInt(id) : id;
            }

            admin_service.delete(entity, realId);
        } catch (Exception e) {
            System.err.println("Error al eliminar " + entity + " con id " + id + ": " + e.getMessage());
            e.printStackTrace();
        }
        return "redirect:/admin/list/" + entity;
    }

    private void loadReferenceData(String entity, Model model) {
        switch (entity.toLowerCase()) {
            case "movie_cast":
                model.addAttribute("movies", admin_service.findTop100("movie"));
                model.addAttribute("persons", admin_service.findTop100("person"));
                model.addAttribute("genders", admin_service.findAll("gender"));
                break;
            case "movie_crew":
                model.addAttribute("movies", admin_service.findTop100("movie"));
                model.addAttribute("persons", admin_service.findTop100("person"));
                model.addAttribute("departments", admin_service.findAll("department"));
                break;
            case "movie":
                model.addAttribute("allGenres", admin_service.findAll("genre"));
                model.addAttribute("allCountries", admin_service.findAll("country"));
                break;
        }
    }

    private String getTemplateForEntity(String entity) {
        return switch (entity.toLowerCase()) {
            case "movie" -> "movie-form";
            case "genre" -> "genre-form";
            case "person" -> "person-form";
            case "country" -> "country-form";
            case "movie_cast" -> "movie-cast-form";
            case "movie_crew" -> "movie-crew-form";
            case "keyword" -> "keyword-form";
            case "department" -> "department-form";
            case "language" -> "language-form";
            case "language_role" -> "language-role-form";
            case "gender" -> "gender-form";
            default -> "dashboard";
        };
    }
}