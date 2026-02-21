package ru.prplhd.tennisscoreboard.dto.match;

public record ScoreDto(String firstPlayerPoints,
                       String SecondPlayerPoints,
                       int firstPlayerGames,
                       int secondPlayerGames,
                       int firstPlayerSets,
                       int secondPlayerSets) {}