package com.campusfind.dto;

import com.campusfind.entity.FoundItem.FoundStatus;
import jakarta.validation.constraints.NotNull;

public record StatusRequest(
        @NotNull FoundStatus status,
        @NotNull Long userId
) {}
