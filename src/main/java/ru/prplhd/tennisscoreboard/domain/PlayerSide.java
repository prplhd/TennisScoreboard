package ru.prplhd.tennisscoreboard.domain;

public enum PlayerSide {
    FIRST,
    SECOND;

    public PlayerSide getOpponent() {
        return switch (this) {
            case FIRST -> SECOND;
            case SECOND -> FIRST;
        };
    }
}