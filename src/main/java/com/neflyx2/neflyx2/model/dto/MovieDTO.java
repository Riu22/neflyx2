package com.neflyx2.neflyx2.model.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.ArrayList;

public record MovieDTO (
        Integer movie_id,
        String title,
        int budget,
        String homepage,
        String overview,
        BigDecimal popularity,
        Object release_date,
        Long revenue,
        int runtime,
        String movie_status,
        String tagline,
        BigDecimal vote_average,
        int vote_count,
        String genres,
        String directors,
        List<CastMemberDTO> cast
) {
    public MovieDTO {
        if (release_date instanceof java.sql.Date sqlDate) {
            release_date = sqlDate.toLocalDate();
        }
    }

    public MovieDTO(
            Integer movie_id,
            String title,
            int budget,
            String homepage,
            String overview,
            BigDecimal popularity,
            Object release_date,
            Long revenue,
            int runtime,
            String movie_status,
            String tagline,
            BigDecimal vote_average,
            int vote_count,
            String genres,
            String directors
    ) {
        this(movie_id, title, budget, homepage, overview, popularity, release_date, revenue, runtime, movie_status, tagline, vote_average, vote_count, genres, directors, null);
    }
}