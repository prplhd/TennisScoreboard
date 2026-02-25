package ru.prplhd.tennisscoreboard.dto.match;

import ru.prplhd.tennisscoreboard.domain.Player;

public record MatchDto(Player firstPlayer, Player secondPlayer, ScoreDto scoreDto, String winner) {}
