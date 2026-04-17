package ru.prplhd.tennisscoreboard.web.dto;

import ru.prplhd.tennisscoreboard.web.dto.match.FinishedMatchDto;

import java.util.List;

public record FinishedMatchesPageDto(
        List<FinishedMatchDto> matchesDtos,
        int page,
        int totalPages,
        int start,
        int end) {}