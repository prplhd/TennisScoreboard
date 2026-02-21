package ru.prplhd.tennisscoreboard.dto.match;

public record MatchDto(String firstPlayerName, String secondPlayerName, ScoreDto scoreDto, String winner) {}
