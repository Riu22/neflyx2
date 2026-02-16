package com.neflyx2.neflyx2.service;

import com.neflyx2.neflyx2.model.dto.EntityDTO;
import com.neflyx2.neflyx2.model.entiti.*;
import com.neflyx2.neflyx2.dao.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class admin_service {

    @Autowired
    private Map<String, JpaRepository<?, ?>> repositories;

    public Page<EntityDTO> findPageDTO(String entity, int page, int size, Integer movieId) {
        Pageable pageable = PageRequest.of(page, size);

        if (movieId != null) {
            if (entity.equals("movie_cast")) {
                movie_cast_repository repo = (movie_cast_repository) repositories.get("movie_cast_repository");
                return repo.findByMovieId(movieId, pageable).map(this::convertToDTO);
            } else if (entity.equals("movie_crew")) {
                movie_crew_repository repo = (movie_crew_repository) repositories.get("movie_crew_repository");
                return repo.findByMovieId(movieId, pageable).map(this::convertToDTO);
            }
        }

        return getRepo(entity).findAll(pageable).map(this::convertToDTO);
    }

    private EntityDTO convertToDTO(Object obj) {
        return switch (obj) {
            case movie m -> new EntityDTO(String.valueOf(m.getMovie_id()), m.getTitle(), "⭐ Pop: " + m.getPopularity());
            case person p -> new EntityDTO(String.valueOf(p.getPerson_id()), p.getPerson_name(), "ID: " + p.getPerson_id());

            case country c -> new EntityDTO(String.valueOf(c.getCountry_id()), c.getMainInfo(), c.getSecondaryInfo());
            case genre g -> new EntityDTO(String.valueOf(g.getGenre_id()), g.getMainInfo(), g.getSecondaryInfo());
            case keyword k -> new EntityDTO(String.valueOf(k.getKeyword_id()), k.getMainInfo(), k.getSecondaryInfo());
            case language l -> new EntityDTO(String.valueOf(l.getLanguage_id()), l.getMainInfo(), l.getSecondaryInfo());
            case department d -> new EntityDTO(String.valueOf(d.getDepartment_id()), d.getMainInfo(), d.getSecondaryInfo());
            case language_role lr -> new EntityDTO(String.valueOf(lr.getRole_id()), lr.getMainInfo(), lr.getSecondaryInfo());

            case movie_crew mcr -> new EntityDTO(
                    mcr.getId().getMovie_id() + "::" + mcr.getId().getPerson_id() + "::" + mcr.getId().getDepartment_id() + "::" + mcr.getId().getJob(),
                    mcr.getMovie().getTitle() + " ➔ " + mcr.getPerson().getPerson_name(),
                    "🛠️ " + mcr.getJob());

            case movie_cast mc -> new EntityDTO(
                    mc.getId().getMovie_id() + "::" + mc.getId().getPerson_id() + "::" + mc.getId().getGender_id() + "::" + mc.getId().getCharacter_name(),
                    mc.getMovie().getTitle() + " ➔ " + mc.getPerson().getPerson_name(),
                    "🎭 " + mc.getCharacter_name());

            default -> new EntityDTO("0", "Desconocido (" + obj.getClass().getSimpleName() + ")", "N/A");
        };
    }


    @Transactional
    public void save(String entity, Object data) {
        getRepo(entity).save(data);
    }

    @Transactional(readOnly = true)
    public Object findById(String entity, Object id) {
        return getRepo(entity).findById(id).orElse(null);
    }

    @Transactional
    public void delete(String entity, Object id) {
        getRepo(entity).deleteById(id);
    }

    public Object createEntityInstance(String entity) {
        return switch (entity.toLowerCase()) {
            case "movie" -> new movie();
            case "person" -> new person();
            case "country" -> new country();
            case "genre" -> new genre();
            case "keyword" -> new keyword();
            case "language" -> new language();
            case "department" -> new department();
            case "language_role" -> new language_role();
            case "movie_crew" -> new movie_crew();
            case "movie_cast" -> new movie_cast();
            default -> new Object();
        };
    }

    @SuppressWarnings("unchecked")
    private JpaRepository<Object, Object> getRepo(String entity) {
        return (JpaRepository<Object, Object>) repositories.get(entity.toLowerCase() + "_repository");
    }


    public List<Object> findTop100(String entity) {
        return getRepo(entity).findAll(PageRequest.of(0, 100)).getContent().stream().map(o -> (Object)o).toList();
    }

    public List<?> findAll(String entity) {
        return getRepo(entity).findAll();
    }

    public Object getEntityForEdit(String entity, String id) {
        if ("0".equals(id)) {
            return createEntityInstance(entity);
        }
        if (entity.equals("movie_cast") || entity.equals("movie_crew")) {
            return findById(entity, parseCompositeId(entity, id));
        }
        return findById(entity, Integer.parseInt(id));
    }

    private Object parseCompositeId(String entity, String id) {
        String[] parts = id.split("::");
        if (entity.equals("movie_cast")) {
            movie_cast_id mid = new movie_cast_id();
            mid.setMovie_id(Integer.parseInt(parts[0]));
            mid.setPerson_id(Integer.parseInt(parts[1]));
            mid.setGender_id(Integer.parseInt(parts[2]));
            mid.setCharacter_name(parts[3]);
            return mid;
        } else if (entity.equals("movie_crew")) {
            movie_crew_id mid = new movie_crew_id();
            mid.setMovie_id(Integer.parseInt(parts[0]));
            mid.setPerson_id(Integer.parseInt(parts[1]));
            mid.setDepartment_id(Integer.parseInt(parts[2]));
            mid.setJob(parts[3]);
            return mid;
        }
        return id;
    }
}