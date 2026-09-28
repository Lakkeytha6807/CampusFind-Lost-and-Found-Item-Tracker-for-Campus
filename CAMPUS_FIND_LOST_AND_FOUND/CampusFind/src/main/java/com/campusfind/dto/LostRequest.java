package com.campusfind.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record LostRequest(
        @NotBlank String description,
        @NotBlank String location,
        @NotNull LocalDate lostDate,
        @NotNull Long categoryId,
        @NotNull Long userId
) {}
