package com.neflyx2.neflyx2.model.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.sql.Date;

public record MovieListDTO(
        Integer movieId,
        String title,
        Object releaseDate,
        BigDecimal voteAverage,
        String genres,
        String directors
) {
    public MovieListDTO {
        if (releaseDate instanceof Date sqlDate) {
            releaseDate = sqlDate.toLocalDate();
        }
    }

    public LocalDate getFormattedReleaseDate() {
        return releaseDate instanceof LocalDate ld ? ld : null;
    }
}