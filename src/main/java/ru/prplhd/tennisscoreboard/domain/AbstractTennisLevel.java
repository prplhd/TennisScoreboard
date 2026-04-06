package ru.prplhd.tennisscoreboard.domain;

import ru.prplhd.tennisscoreboard.exception.SamePlayerException;

import java.util.Objects;
import java.util.Optional;

public abstract class AbstractTennisLevel {

    protected final Player firstPlayer;
    protected final Player secondPlayer;
    protected Player winner;

    protected AbstractTennisLevel(Player firstPlayer, Player secondPlayer) {
        if (Objects.equals(firstPlayer.name(), secondPlayer.name())) {
            throw new SamePlayerException();
        }

        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
    }

    protected Player getOpponent(Player player) {
        return Objects.equals(player, firstPlayer) ? secondPlayer : firstPlayer;
    }

    protected Optional<Player> getWinner() {
        if (Objects.equals(firstPlayer, winner)) {
            return Optional.of(firstPlayer);
        }
        if (Objects.equals(secondPlayer, winner)) {
            return Optional.of(secondPlayer);
        }
        return Optional.empty();
    }

    protected boolean isFinished() {
        return getWinner().isPresent();
    }

    protected abstract void pointWonBy(Player player);
}