package ru.prplhd.tennisscoreboard.exception;

public class MatchAlreadyFinishedException extends RuntimeException {
    public MatchAlreadyFinishedException() {
        super("Match already finished");
    }
}
