package com.neflyx2.neflyx2.service;

import com.neflyx2.neflyx2.dao.movie_repository;
import com.neflyx2.neflyx2.model.dto.CastMemberDTO;
import com.neflyx2.neflyx2.model.dto.MovieDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class detail_service {

    @Autowired
    movie_repository movie_repository;

    public Optional<MovieDTO> details(int id) {
        Optional<MovieDTO> movieOpt = movie_repository.findMovieDetailById(id);

        if (movieOpt.isPresent()) {
            MovieDTO base = movieOpt.get();
            List<CastMemberDTO> cast = movie_repository.findCastByMovieId(id);

            return Optional.of(new MovieDTO(
                    base.movie_id(),
                    base.title(),
                    base.budget(),
                    base.homepage(),
                    base.overview(),
                    base.popularity(),
                    base.release_date(),
                    base.revenue(),
                    base.runtime(),
                    base.movie_status(),
                    base.tagline(),
                    base.vote_average(),
                    base.vote_count(),
                    base.genres(),
                    base.directors(),
                    cast
            ));
        }

        return Optional.empty();
    }
    public List<String> getAutocompleteSuggestions(String term) {
        if (term == null || term.length() < 3) return List.of();
        return movie_repository.findSuggestions(term);
    }
}