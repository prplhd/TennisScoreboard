package ru.prplhd.tennisscoreboard.service;

import ru.prplhd.tennisscoreboard.domain.snapshot.MatchSnapshot;
import ru.prplhd.tennisscoreboard.web.dto.NewMatchRequestDto;
import ru.prplhd.tennisscoreboard.web.dto.match.MatchPlayerIdsDto;
import ru.prplhd.tennisscoreboard.web.dto.match.MatchScoreboardDto;

import java.util.UUID;

public interface OngoingMatchService {
    UUID createNewMatch(NewMatchRequestDto newMatchRequestDto);

    MatchSnapshot getMatchSnapshot(UUID matchUUID);

    MatchSnapshot applyPoint(UUID matchUUID, Integer scorerId);

    void deleteMatch(UUID matchUUID);

    MatchPlayerIdsDto getPlayerIds(MatchScoreboardDto matchScoreboardDto);
}
