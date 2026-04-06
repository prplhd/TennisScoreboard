package ru.prplhd.tennisscoreboard.exception;

public class SamePlayerException extends RuntimeException {
    public SamePlayerException() {
        super("firstPlayer and secondPlayer must be different");
    }
}