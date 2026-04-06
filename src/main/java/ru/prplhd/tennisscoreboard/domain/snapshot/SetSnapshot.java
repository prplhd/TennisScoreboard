package ru.prplhd.tennisscoreboard.domain.snapshot;

public record SetSnapshot(int firstPlayerGames, int secondPlayerGames, GameSnapshot game) {
}