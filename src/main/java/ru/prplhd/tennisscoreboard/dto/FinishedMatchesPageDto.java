package ru.prplhd.tennisscoreboard.dto;

import ru.prplhd.tennisscoreboard.dto.match.FinishedMatchDto;

import java.util.List;

public record FinishedMatchesPageDto(
        List<FinishedMatchDto> matchesDtos,
        int page,
        int totalPages,
        int start,
        int end) {}