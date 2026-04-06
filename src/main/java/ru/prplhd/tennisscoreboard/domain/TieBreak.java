package ru.prplhd.tennisscoreboard.domain;

import ru.prplhd.tennisscoreboard.domain.snapshot.GameSnapshot;
import ru.prplhd.tennisscoreboard.exception.AlreadyFinishedException;

public class TieBreak extends Game {
    private static final int INITIAL_TIEBREAK_POINT = 0;
    private static final int MIN_POINTS_TO_WIN = 7;
    private static final int MIN_POINTS_LEAD_TO_WIN = 2;

    private final PlayerScores<Integer> points = new PlayerScores<>(firstPlayer, secondPlayer, INITIAL_TIEBREAK_POINT, INITIAL_TIEBREAK_POINT);

    public TieBreak(Player firstPlayer, Player secondPlayer) {
        super(firstPlayer, secondPlayer);
    }

    @Override
    public GameSnapshot getSnapshot() {
        return new GameSnapshot(
                String.valueOf(points.get(firstPlayer)),
                String.valueOf(points.get(secondPlayer))
        );
    }

    @Override
    protected void pointWonBy(Player scorer) {
        if (isFinished()) {
            throw new AlreadyFinishedException("Game already finished");
        }

        int scorerPoints = points.get(scorer) + 1;
        points.set(scorer, scorerPoints);

        if (isWin(scorer, scorerPoints)) {
            winner = scorer;
        }
    }

    private boolean isWin(Player scorer, int scorerPoints) {
        int opponentPoints = points.get(getOpponent(scorer));
        int leadPoints = scorerPoints - opponentPoints;

        return scorerPoints >= MIN_POINTS_TO_WIN && leadPoints >= MIN_POINTS_LEAD_TO_WIN;
    }

}
