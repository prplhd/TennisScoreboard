package ru.prplhd.tennisscoreboard.domain.score;

import ru.prplhd.tennisscoreboard.domain.PlayerSide;

public class TiebreakPointScore implements PointScore {

    private int firstPlayerPoints = 0;
    private int secondPlayerPoints = 0;

    private static final int MIN_POINTS_TO_WIN = 7;
    private static final int MIN_POINTS_LEAD_TO_WIN = 2;

    @Override
    public ScoreEffect pointWonBy(PlayerSide scorerPlayerSide) {
        addPoint(scorerPlayerSide);

        int scorerPoints = getPoints(scorerPlayerSide);
        PlayerSide opponentPlayerSide = scorerPlayerSide.getOpponent();
        int opponentPoints = getPoints(opponentPlayerSide);

        int leadPoints = scorerPoints - opponentPoints;
        if (scorerPoints >= MIN_POINTS_TO_WIN && leadPoints >= MIN_POINTS_LEAD_TO_WIN) {

            return ScoreEffect.GAME_WON;
        }

        return ScoreEffect.NONE;
    }

    @Override
    public void resetPoints() {
        firstPlayerPoints = 0;
        secondPlayerPoints = 0;
    }

    @Override
    public String getFirstPlayerPoints() {
        return String.valueOf(firstPlayerPoints);
    }

    @Override
    public String getSecondPlayerPoints() {
        return String.valueOf(secondPlayerPoints);
    }

    @Override
    public PointScoreMode getMode() {
        return PointScoreMode.TIEBREAK;
    }

    private int getPoints(PlayerSide playerSide) {
        return switch (playerSide) {
            case FIRST -> firstPlayerPoints;
            case SECOND -> secondPlayerPoints;
        };
    }

    private void addPoint(PlayerSide playerSide) {
        switch (playerSide) {
            case FIRST -> firstPlayerPoints++;
            case SECOND -> secondPlayerPoints++;
        }
    }
}
