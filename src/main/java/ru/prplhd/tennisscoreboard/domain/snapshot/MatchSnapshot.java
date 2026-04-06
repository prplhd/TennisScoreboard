package ru.prplhd.tennisscoreboard.domain.snapshot;

import ru.prplhd.tennisscoreboard.domain.Player;

public record MatchSnapshot(
        Player firstPlayer,
        Player secondPlayer,
        Player winner,
        int firstPlayerSets,
        int secondPlayerSets,
        SetSnapshot set
) {
}