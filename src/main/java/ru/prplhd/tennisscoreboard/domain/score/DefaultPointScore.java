package ru.prplhd.tennisscoreboard.domain.score;

import ru.prplhd.tennisscoreboard.domain.PlayerSide;

public class DefaultPointScore implements PointScore {

    private PointValue firstPlayerPoints = PointValue.LOVE;
    private PointValue secondPlayerPoints = PointValue.LOVE;

    @Override
    public ScoreEffect pointWonBy(PlayerSide scorerPlayerSide) {
        PointValue scorerPoints = getPoints(scorerPlayerSide);
        PlayerSide opponentPlayerSide = scorerPlayerSide.getOpponent();
        PointValue opponentPoints = getPoints(opponentPlayerSide);

        switch (scorerPoints) {
            case LOVE -> scorerPoints = PointValue.FIFTEEN;
            case FIFTEEN -> scorerPoints = PointValue.THIRTY;
            case THIRTY -> scorerPoints = PointValue.FORTY;
            case FORTY -> {
                if (opponentPoints == PointValue.ADVANTAGE) {
                    setPoints(opponentPlayerSide, PointValue.FORTY);
                    break;
                }

                if (opponentPoints == PointValue.FORTY) {
                    scorerPoints = PointValue.ADVANTAGE;
                    break;
                }

                return ScoreEffect.GAME_WON;
            }
            case ADVANTAGE -> {
                return ScoreEffect.GAME_WON;
            }

        }
        setPoints(scorerPlayerSide, scorerPoints);

        return ScoreEffect.NONE;
    }

    @Override
    public void resetPoints() {
        firstPlayerPoints = PointValue.LOVE;
        secondPlayerPoints = PointValue.LOVE;
    }

    @Override
    public String getFirstPlayerPoints() {
        return firstPlayerPoints.getDisplayedValue();
    }

    @Override
    public String getSecondPlayerPoints() {
        return secondPlayerPoints.getDisplayedValue();
    }

    @Override
    public PointScoreMode getMode() {
        return PointScoreMode.DEFAULT;
    }

    private PointValue getPoints(PlayerSide playerSide) {
        return switch (playerSide) {
            case FIRST -> firstPlayerPoints;
            case SECOND -> secondPlayerPoints;
        };
    }

    private void setPoints(PlayerSide playerSide, PointValue pointValue) {
        switch (playerSide) {
            case FIRST -> firstPlayerPoints = pointValue;
            case SECOND -> secondPlayerPoints = pointValue;
        }
    }

    private enum PointValue {
        LOVE("0"),
        FIFTEEN("15"),
        THIRTY("30"),
        FORTY("40"),
        ADVANTAGE("AD");

        private final String displayedValue;

        PointValue(String displayedValue) {
            this.displayedValue = displayedValue;
        }

        String getDisplayedValue() {
            return displayedValue;
        }
    }
}
