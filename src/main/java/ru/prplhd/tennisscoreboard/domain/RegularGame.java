package ru.prplhd.tennisscoreboard.domain;


import ru.prplhd.tennisscoreboard.domain.snapshot.GameSnapshot;
import ru.prplhd.tennisscoreboard.exception.AlreadyFinishedException;

public class RegularGame extends Game {

    private final PlayerScores<RegularGamePoint> points = new PlayerScores<>(firstPlayer, secondPlayer, RegularGamePoint.LOVE, RegularGamePoint.LOVE);

    public RegularGame(Player firstPlayer, Player secondPlayer) {
        super(firstPlayer, secondPlayer);
    }

    @Override
    public GameSnapshot getSnapshot() {
        return new GameSnapshot(
                points.get(firstPlayer).asString(),
                points.get(secondPlayer).asString()
        );
    }

    @Override
    protected void pointWonBy(Player scorer) {
        if (isFinished()) {
            throw new AlreadyFinishedException("Game already finished");
        }

        RegularGamePoint scorerPoints = points.get(scorer);
        RegularGamePoint opponentPoints = points.get(getOpponent(scorer));

        if (scorerPoints == RegularGamePoint.FORTY) {
            handlePointAtForty(scorer, opponentPoints);
            return;
        }

        if (scorerPoints == RegularGamePoint.ADVANTAGE) {
            winner = scorer;
            return;
        }

        points.set(scorer, scorerPoints.next());
    }

    private void handlePointAtForty(Player scorer, RegularGamePoint opponentPoints) {
        if (opponentPoints == RegularGamePoint.ADVANTAGE) {
            points.set(getOpponent(scorer), RegularGamePoint.FORTY);
            return;
        }

        if (opponentPoints == RegularGamePoint.FORTY) {
            points.set(scorer, RegularGamePoint.ADVANTAGE);
            return;
        }

        winner = scorer;
    }

    private enum RegularGamePoint {

        LOVE("0"),
        FIFTEEN("15"),
        THIRTY("30"),
        FORTY("40"),
        ADVANTAGE("AD");

        private final String displayValue;

        RegularGamePoint(String displayValue) {
            this.displayValue = displayValue;
        }

        public RegularGamePoint next() {
            if (this == ADVANTAGE) {
                throw new IllegalStateException("Cannot get next point after ADVANTAGE");
            }

            return values()[this.ordinal() + 1];
        }

        public String asString() {
            return displayValue;
        }
    }
}
