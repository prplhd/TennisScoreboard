package ru.prplhd.tennisscoreboard.dto.match.ongoing;

import java.util.List;

public record ScoreDto(String firstPlayerPoints,
                       String secondPlayerPoints,
                       int firstPlayerGames,
                       int secondPlayerGames,
                       int firstPlayerSets,
                       int secondPlayerSets,
                       List<SetScoreDto> finishedSetsScore) {}