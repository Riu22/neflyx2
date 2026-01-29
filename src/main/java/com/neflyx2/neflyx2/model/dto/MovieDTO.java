package com.neflyx2.neflyx2.model.dto;

import java.math.BigDecimal;

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
        int vote_count
){
    public MovieDTO {
        if (release_date instanceof java.sql.Date sqlDate) {
            release_date = sqlDate.toLocalDate();
        }
    }
}
