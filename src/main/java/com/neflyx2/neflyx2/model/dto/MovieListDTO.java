package com.neflyx2.neflyx2.model.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record MovieListDTO(
        Integer movieId,
        String title,
        LocalDate releaseDate,
        BigDecimal voteAverage,
        String genres,
        String directors
) {}