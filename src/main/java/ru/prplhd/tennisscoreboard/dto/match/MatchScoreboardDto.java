package ru.prplhd.tennisscoreboard.dto.match;

public record MatchScoreboardDto(
        String firstPlayer,
        String secondPlayer,
        String winner,
        int firstPlayerSets,
        int secondPlayerSets,
        int firstPlayerGames,
        int secondPlayerGames,
        String firstPlayerPoints,
        String secondPlayerPoints
) {
}