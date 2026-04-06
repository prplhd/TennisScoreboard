package ru.prplhd.tennisscoreboard.domain;

import ru.prplhd.tennisscoreboard.exception.NotFoundException;

import java.util.Objects;

public class PlayerScores<T> {

    private final Player firstPlayer;
    private final Player secondPlayer;
    private T firstPlayerValue;
    private T secondPlayerValue;

    public PlayerScores(Player firstPlayer, Player secondPlayer, T firstPlayerValue, T secondPlayerValue) {
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
        this.firstPlayerValue = firstPlayerValue;
        this.secondPlayerValue = secondPlayerValue;
    }

    public T get(Player player) {
        if (Objects.equals(player, firstPlayer)) {
            return firstPlayerValue;
        }
        if (Objects.equals(player, secondPlayer)) {
            return secondPlayerValue;
        }
        throw new NotFoundException("Player '" + player.name() + "' does not belong to this score");
    }

    public void set(Player player, T value) {
        if (Objects.equals(player, firstPlayer)) {
            firstPlayerValue = value;
            return;
        }
        if (Objects.equals(player, secondPlayer)) {
            secondPlayerValue = value;
            return;
        }
        throw new NotFoundException("Player '" + player.name() + "' does not belong to this score");
    }
}
