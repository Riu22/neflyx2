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
            case movie m -> new EntityDTO(
                    String.valueOf(m.getMovie_id()),
                    m.getTitle(),
                    "⭐ Pop: " + m.getPopularity()
            );
            case person p -> new EntityDTO(
                    String.valueOf(p.getPerson_id()),
                    p.getPerson_name(),
                    "ID: " + p.getPerson_id()
            );
            case genre g -> new EntityDTO(
                    String.valueOf(g.getGenre_id()),
                    g.getGenre_name(),
                    "Género"
            );
            case country c -> new EntityDTO(
                    String.valueOf(c.getCountry_id()),
                    c.getCountry_name(),
                    "ISO: " + c.getCountry_iso_code()
            );
            case keyword k -> new EntityDTO(
                    String.valueOf(k.getKeyword_id()),
                    k.getKeyword_name(),
                    "🏷️ Palabra clave"
            );
            case department d -> new EntityDTO(
                    String.valueOf(d.getDepartment_id()),
                    d.getDepartment_name(),
                    "🏢 Departamento"
            );
            case language l -> new EntityDTO(
                    String.valueOf(l.getLanguage_id()),
                    l.getLanguage_name(),
                    "🌐 " + l.getLanguage_code()
            );
            case language_role lr -> new EntityDTO(
                    String.valueOf(lr.getRole_id()),
                    lr.getLanguage_role(),
                    "📋 Rol de idioma"
            );
            case gender gnd -> new EntityDTO(
                    String.valueOf(gnd.getGender_id()),
                    gnd.getGender(),
                    "⚧ Género"
            );
            case movie_cast mc -> new EntityDTO(
                    mc.getId().getMovie_id() + "-" + mc.getId().getPerson_id() + "-" +
                            (mc.getId().getGender_id() != null ? mc.getId().getGender_id() : "0"),
                    mc.getMovie().getTitle() + " ➔ " + mc.getPerson().getPerson_name(),
                    "🎭 " + (mc.getCharacter_name() != null ? mc.getCharacter_name() : "Sin personaje")
            );
            case movie_crew mcr -> new EntityDTO(
                    mcr.getId().getMovie_id() + "-" + mcr.getId().getPerson_id() + "-" +
                            (mcr.getId().getDepartment_id() != null ? mcr.getId().getDepartment_id() : "0"),
                    mcr.getMovie().getTitle() + " ➔ " + mcr.getPerson().getPerson_name(),
                    "🛠️ " + (mcr.getJob() != null ? mcr.getJob() : "Sin especificar")
            );
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
            case "keyword" -> new keyword();
            case "department" -> new department();
            case "language" -> new language();
            case "language_role" -> new language_role();
            case "gender" -> new gender();
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
        return getRepo(entity).findAll(PageRequest.of(0, 100)).getContent()
                .stream()
                .map(o -> (Object)o)
                .toList();
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