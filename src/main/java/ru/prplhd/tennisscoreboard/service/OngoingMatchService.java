package ru.prplhd.tennisscoreboard.service;

import ru.prplhd.tennisscoreboard.dto.match.MatchDto;
import ru.prplhd.tennisscoreboard.dto.request.NewMatchRequestDto;

import java.util.UUID;

public interface OngoingMatchService {
    UUID createNewMatch(NewMatchRequestDto newMatchRequestDto);

    MatchDto getMatchScoreboard(UUID matchUUID);

    MatchDto applyPoint(UUID matchUUID, Integer scorerId);

    void deleteMatch(UUID matchUUID);
}
