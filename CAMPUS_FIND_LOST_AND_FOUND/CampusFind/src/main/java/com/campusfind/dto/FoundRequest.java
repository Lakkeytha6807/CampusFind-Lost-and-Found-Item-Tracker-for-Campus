package com.campusfind.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record FoundRequest(
        @NotBlank String description,
        @NotBlank String location,
        @NotNull LocalDate foundDate,
        @NotNull Long categoryId,
        @NotNull Long staffId
) {}
