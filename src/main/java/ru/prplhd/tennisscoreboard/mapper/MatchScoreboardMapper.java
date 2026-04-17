package ru.prplhd.tennisscoreboard.mapper;

import ru.prplhd.tennisscoreboard.domain.snapshot.MatchSnapshot;
import ru.prplhd.tennisscoreboard.web.dto.match.MatchScoreboardDto;

public final class MatchScoreboardMapper {

    private MatchScoreboardMapper() {}

    public static MatchScoreboardDto toDto(MatchSnapshot snapshot) {
        return new MatchScoreboardDto(
                snapshot.firstPlayer().name(),
                snapshot.secondPlayer().name(),
                snapshot.winner() != null ? snapshot.winner().name() : null,
                snapshot.firstPlayerSets(),
                snapshot.secondPlayerSets(),
                snapshot.set().firstPlayerGames(),
                snapshot.set().secondPlayerGames(),
                snapshot.set().game().firstPlayerPoints(),
                snapshot.set().game().secondPlayerPoints()
        );
    }
}