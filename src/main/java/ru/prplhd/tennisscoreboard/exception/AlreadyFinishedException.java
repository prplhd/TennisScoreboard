package ru.prplhd.tennisscoreboard.exception;

public class AlreadyFinishedException extends RuntimeException {
    public AlreadyFinishedException(String message) {
        super(message);
    }
}
