package ru.prplhd.tennisscoreboard.service;

import ru.prplhd.tennisscoreboard.dto.match.FinishedMatchesDto;
import ru.prplhd.tennisscoreboard.dto.match.MatchDto;

import java.util.List;

public interface FinishedMatchesPersistenceService {

    List<FinishedMatchesDto> findAllMatches();
    void saveMatch(MatchDto matchDto);
}