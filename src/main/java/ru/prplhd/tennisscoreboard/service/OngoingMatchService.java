package ru.prplhd.tennisscoreboard.service;

import ru.prplhd.tennisscoreboard.domain.snapshot.MatchSnapshot;
import ru.prplhd.tennisscoreboard.dto.NewMatchRequestDto;
import ru.prplhd.tennisscoreboard.dto.match.MatchPlayerIdsDto;
import ru.prplhd.tennisscoreboard.dto.match.MatchScoreboardDto;

import java.util.UUID;

public interface OngoingMatchService {
    UUID createNewMatch(NewMatchRequestDto newMatchRequestDto);

    MatchSnapshot getMatchScoreboard(UUID matchUUID);

    MatchSnapshot applyPoint(UUID matchUUID, Integer scorerId);

    void deleteMatch(UUID matchUUID);

    MatchPlayerIdsDto getPlayerIds(MatchScoreboardDto matchScoreboardDto);
}
