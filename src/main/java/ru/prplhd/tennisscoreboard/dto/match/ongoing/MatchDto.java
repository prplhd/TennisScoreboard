package ru.prplhd.tennisscoreboard.dto.match.ongoing;

import ru.prplhd.tennisscoreboard.domain.Player;

public record MatchDto(Player firstPlayer, Player secondPlayer, ScoreDto scoreDto, Player winner) {}
