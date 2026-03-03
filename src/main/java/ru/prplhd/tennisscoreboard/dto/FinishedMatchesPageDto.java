package ru.prplhd.tennisscoreboard.dto;

import ru.prplhd.tennisscoreboard.dto.match.finished.FinishedMatchesDto;

import java.util.List;

public record FinishedMatchesPageDto(
        List<FinishedMatchesDto> matchesDtos,
        int page,
        int totalPages,
        int start,
        int end) {}