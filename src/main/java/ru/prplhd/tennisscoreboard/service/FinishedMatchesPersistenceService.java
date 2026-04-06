package ru.prplhd.tennisscoreboard.service;

import ru.prplhd.tennisscoreboard.dto.FinishedMatchesPageDto;
import ru.prplhd.tennisscoreboard.dto.match.MatchScoreboardDto;

public interface FinishedMatchesPersistenceService {

    FinishedMatchesPageDto getMatchesPage(String pageParameterValue);
    FinishedMatchesPageDto getMatchesPage(String pageParameterValue, String name);
    void saveMatch(MatchScoreboardDto matchScoreboardDto);
}