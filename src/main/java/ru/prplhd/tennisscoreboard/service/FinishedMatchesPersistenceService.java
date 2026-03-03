package ru.prplhd.tennisscoreboard.service;

import ru.prplhd.tennisscoreboard.dto.FinishedMatchesPageDto;
import ru.prplhd.tennisscoreboard.dto.match.ongoing.MatchDto;

public interface FinishedMatchesPersistenceService {

    FinishedMatchesPageDto getMatchesPage(String pageParameterValue);
    void saveMatch(MatchDto matchDto);
}