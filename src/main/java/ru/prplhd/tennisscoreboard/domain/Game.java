package ru.prplhd.tennisscoreboard.domain;

import ru.prplhd.tennisscoreboard.domain.snapshot.GameSnapshot;

public abstract class Game extends AbstractTennisLevel {

    protected Game(Player firstPlayer, Player secondPlayer) {
        super(firstPlayer, secondPlayer);
    }

    public abstract GameSnapshot getSnapshot();
}