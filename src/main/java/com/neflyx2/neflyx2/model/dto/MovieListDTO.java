package com.neflyx2.neflyx2.model.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.sql.Date;

public record MovieListDTO(
        Integer movieId,
        String title,
        Object releaseDate, // Lo recibimos como Object para evitar el ClassCastException
        BigDecimal voteAverage,
        String genres,
        String directors
) {
    // Este es el constructor compacto. Solo existe ESTE constructor.
    public MovieListDTO {
        // Convertimos el releaseDate solo si es un java.sql.Date
        if (releaseDate instanceof Date sqlDate) {
            releaseDate = sqlDate.toLocalDate();
        }
    }

    // Opcional: Un método para obtenerlo siempre como LocalDate en tu Frontend/Service
    public LocalDate getFormattedReleaseDate() {
        return releaseDate instanceof LocalDate ld ? ld : null;
    }
}