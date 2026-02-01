package com.neflyx2.neflyx2.service;

import com.neflyx2.neflyx2.model.dto.EntityDTO;
import com.neflyx2.neflyx2.model.entiti.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class admin_service {

    @Autowired
    private Map<String, JpaRepository<?, ?>> repositories;

    public Page<EntityDTO> findPageDTO(String entity, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return getRepo(entity).findAll(pageable).map(this::convertToDTO);
    }

    private EntityDTO convertToDTO(Object obj) {
        return switch (obj) {
            case movie m -> new EntityDTO(String.valueOf(m.getMovie_id()), m.getTitle(), "⭐ Pop: " + m.getPopularity());
            case person p -> new EntityDTO(String.valueOf(p.getPerson_id()), p.getPerson_name(), "ID: " + p.getPerson_id());
            case genre g -> new EntityDTO(String.valueOf(g.getGenre_id()), g.getGenre_name(), "Género");
            case country c -> new EntityDTO(String.valueOf(c.getCountry_id()), c.getCountry_name(), "ISO: " + c.getCountry_iso_code());
            case movie_cast mc -> new EntityDTO(String.valueOf(mc.getMovie().getMovie_id()),
                    mc.getMovie().getTitle() + " ➔ " + mc.getPerson().getPerson_name(), "🎭 " + mc.getCharacter_name());
            case movie_crew mcr -> new EntityDTO(String.valueOf(mcr.getMovie().getMovie_id()),
                    mcr.getMovie().getTitle() + " ➔ " + mcr.getPerson().getPerson_name(), "🛠️ " + mcr.getJob());
            default -> new EntityDTO("0", "Desconocido", "N/A");
        };
    }

    public Object createEntityInstance(String entity) {
        return switch (entity.toLowerCase()) {
            case "movie" -> new movie();
            case "genre" -> new genre();
            case "person" -> new person();
            case "country" -> new country();
            case "movie_cast" -> new movie_cast();
            case "movie_crew" -> new movie_crew();
            default -> throw new IllegalArgumentException("Entidad no reconocida: " + entity);
        };
    }

    public Object findById(String entity, Object id) {
        return getRepo(entity).findById(id).orElse(null);
    }

    @Transactional
    public void save(String entity, Object data) {
        getRepo(entity).save(data);
    }

    @Transactional
    public void delete(String entity, Object id) {
        getRepo(entity).deleteById(id);
    }

    public List<Object> findTop100(String entity) {
        return getRepo(entity).findAll(PageRequest.of(0, 100)).getContent().stream().map(o -> (Object)o).toList();
    }

    public List<?> findAll(String entity) {
        return getRepo(entity).findAll();
    }

    @SuppressWarnings("unchecked")
    private JpaRepository<Object, Object> getRepo(String entity) {
        String name = entity.toLowerCase() + "_repository";
        JpaRepository<Object, Object> repo = (JpaRepository<Object, Object>) repositories.get(name);
        if (repo == null) throw new RuntimeException("Repositorio no encontrado: " + name);
        return repo;
    }
}