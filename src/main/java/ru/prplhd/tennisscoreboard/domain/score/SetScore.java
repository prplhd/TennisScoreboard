package ru.prplhd.tennisscoreboard.domain.score;

import ru.prplhd.tennisscoreboard.domain.PlayerSide;

public class SetScore {

    private int firstPlayerSets = 0;
    private int secondPlayerSets = 0;

    private static final int SETS_TO_WIN_MATCH = 2;

    public ScoreEffect setWonBy(PlayerSide scorerPlayerSide) {
        addSet(scorerPlayerSide);
        int scorerPoints = getSets(scorerPlayerSide);

        if (scorerPoints == SETS_TO_WIN_MATCH) {
            return ScoreEffect.MATCH_WON;
        }

        return ScoreEffect.NONE;
    }

    public int getFirstPlayerSets() {
        return firstPlayerSets;
    }

    public int getSecondPlayerSets() {
        return secondPlayerSets;
    }

    private int getSets(PlayerSide playerSide) {
        return switch (playerSide) {
            case FIRST -> firstPlayerSets;
            case SECOND -> secondPlayerSets;
        };
    }

    private void addSet(PlayerSide playerSide) {
        switch (playerSide) {
            case FIRST -> firstPlayerSets++;
            case SECOND -> secondPlayerSets++;
        }
    }
}
