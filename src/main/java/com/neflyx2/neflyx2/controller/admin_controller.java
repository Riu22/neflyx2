package com.neflyx2.neflyx2.controller;

import com.neflyx2.neflyx2.model.entiti.*;
import com.neflyx2.neflyx2.service.admin_service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

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
    public String list(@PathVariable String entity,
                       @RequestParam(defaultValue = "0") int page,
                       @RequestParam(value = "movieId", required = false) Integer movieId,
                       Model model) {

        var itemsPage = admin_service.findPageDTO(entity, page, 50, movieId);

        model.addAttribute("items", itemsPage.getContent());
        model.addAttribute("entityName", entity);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", itemsPage.getTotalPages());
        model.addAttribute("movieId", movieId);

        if (movieId != null) {
            model.addAttribute("filteredMovie", admin_service.findById("movie", movieId));
        }

        return "list-view";
    }

    @GetMapping("/edit/{entity}")
    public String edit(@PathVariable String entity,
                       @RequestParam(value = "id", required = false) String id, // Ahora es RequestParam
                       @RequestParam(value = "movieId", required = false) Integer movieId,
                       Model model) {

        Object item = (id == null || "0".equals(id)) ?
                admin_service.createEntityInstance(entity) :
                admin_service.findById(entity, parseId(entity, id));

        if ((id == null || "0".equals(id)) && movieId != null) {
            movie m = (movie) admin_service.findById("movie", movieId);
            if (item instanceof movie_crew mcr) mcr.setMovie(m);
            else if (item instanceof movie_cast mc) mc.setMovie(m);
        }

        model.addAttribute("item", item);
        model.addAttribute("entityName", entity);
        model.addAttribute("movieId", movieId);
        loadReferenceData(entity, model);
        return getTemplateForEntity(entity);
    }

    @PostMapping("/save/person")
    public String savePerson(@ModelAttribute("item") person p) {
        admin_service.save("person", p);
        return "redirect:/admin/list/person";
    }

    @PostMapping("/save/country")
    public String saveCountry(@ModelAttribute("item") country c) {
        admin_service.save("country", c);
        return "redirect:/admin/list/country";
    }

    @PostMapping("/save/genre")
    public String saveGenre(@ModelAttribute("item") genre g) {
        admin_service.save("genre", g);
        return "redirect:/admin/list/genre";
    }

    @PostMapping("/save/keyword")
    public String saveKeyword(@ModelAttribute("item") keyword k) {
        admin_service.save("keyword", k);
        return "redirect:/admin/list/keyword";
    }

    @PostMapping("/save/language")
    public String saveLanguage(@ModelAttribute("item") language l) {
        admin_service.save("language", l);
        return "redirect:/admin/list/language";
    }

    @PostMapping("/save/department")
    public String saveDepartment(@ModelAttribute("item") department d) {
        admin_service.save("department", d);
        return "redirect:/admin/list/department";
    }

    @PostMapping("/save/language_role")
    public String saveLanguageRole(@ModelAttribute("item") language_role lr) {
        admin_service.save("language_role", lr);
        return "redirect:/admin/list/language_role";
    }

    @PostMapping("/save/movie")
    public String saveMovie(@ModelAttribute("item") movie m,
                            @RequestParam(value = "redirectTo", required = false) String redirectTo) {
        admin_service.save("movie", m);
        return (redirectTo != null && !redirectTo.isEmpty()) ? "redirect:" + redirectTo : "redirect:/admin/list/movie";
    }

    @PostMapping("/save/movie_crew")
    public String saveMovieCrew(@ModelAttribute("item") movie_crew mcr) {
        mcr.setId(new movie_crew_id(
                mcr.getMovie().getMovie_id(),
                mcr.getPerson().getPerson_id(),
                mcr.getDepartment().getDepartment_id(),
                mcr.getJob()
        ));
        admin_service.save("movie_crew", mcr);
        return "redirect:/admin/list/movie_crew?movieId=" + mcr.getMovie().getMovie_id();
    }

    @PostMapping("/save/movie_cast")
    public String saveMovieCast(@ModelAttribute("item") movie_cast mc) {
        mc.setId(new movie_cast_id(
                mc.getMovie().getMovie_id(),
                mc.getPerson().getPerson_id(),
                mc.getGender().getGender_id(),
                mc.getCharacter_name()
        ));
        admin_service.save("movie_cast", mc);
        return "redirect:/admin/list/movie_cast?movieId=" + mc.getMovie().getMovie_id();
    }

    @GetMapping("/delete/{entity}")
    public String delete(@PathVariable String entity,
                         @RequestParam("id") String id, // Cambiado a RequestParam
                         @RequestParam(value = "movieId", required = false) Integer movieId) {
        admin_service.delete(entity, parseId(entity, id));
        String redirect = "redirect:/admin/list/" + entity;
        return (movieId != null) ? redirect + "?movieId=" + movieId : redirect;
    }

    private Object parseId(String entity, String id) {
        String[] parts = id.split("::", 4);
        if (entity.equals("movie_crew") && parts.length == 4) {
            return new movie_crew_id(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2]), parts[3]);
        } else if (entity.equals("movie_cast") && parts.length == 4) {
            return new movie_cast_id(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2]), parts[3]);
        }
        return id.matches("\\d+") ? Integer.parseInt(id) : id;
    }

    private void loadReferenceData(String entity, Model model) {
        if (entity.equals("movie_cast") || entity.equals("movie_crew") || entity.equals("movie")) {
            Integer movieId = (Integer) model.getAttribute("movieId");
            if (movieId != null) {
                Object singleMovie = admin_service.findById("movie", movieId);
                model.addAttribute("movies", Collections.singletonList(singleMovie));
            } else {
                model.addAttribute("movies", admin_service.findTop100("movie"));
            }
            model.addAttribute("persons", admin_service.findTop100("person"));
            if (entity.equals("movie_cast")) model.addAttribute("genders", admin_service.findAll("gender"));
            if (entity.equals("movie_crew")) model.addAttribute("departments", admin_service.findAll("department"));
            if (entity.equals("movie")) model.addAttribute("allGenres", admin_service.findAll("genre"));
        }
    }

    private String getTemplateForEntity(String entity) {
        return switch (entity.toLowerCase()) {
            case "movie" -> "movie-form";
            case "movie_crew" -> "movie-crew-form";
            case "movie_cast" -> "movie-cast-form";
            case "person" -> "person-form";
            case "country" -> "country-form";
            case "genre" -> "genre-form";
            case "keyword" -> "keyword-form";
            case "language" -> "language-form";
            case "department" -> "department-form";
            case "language_role" -> "language-role-form";
            default -> "dashboard";
        };
    }
}