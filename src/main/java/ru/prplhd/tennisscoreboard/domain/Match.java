package ru.prplhd.tennisscoreboard.domain;

import ru.prplhd.tennisscoreboard.domain.snapshot.MatchSnapshot;
import ru.prplhd.tennisscoreboard.exception.AlreadyFinishedException;

public class Match extends AbstractTennisLevel {

    private static final int SETS_TO_WIN_MATCH = 2;

    private final PlayerScores<Integer> sets = new PlayerScores<>(firstPlayer, secondPlayer, 0, 0);
    private TennisSet currentSet;

    public Match(Player firstPlayer, Player secondPlayer) {
        super(firstPlayer, secondPlayer);
        this.currentSet = new TennisSet(firstPlayer, secondPlayer);
    }

    public MatchSnapshot getSnapshot() {
        return new MatchSnapshot(
                firstPlayer,
                secondPlayer,
                winner,
                sets.get(firstPlayer),
                sets.get(secondPlayer),
                currentSet.getSnapshot()
        );
    }

    @Override
    public void pointWonBy(Player scorer) {
        if (isFinished()) {
            throw new AlreadyFinishedException("Match already finished");
        }

        currentSet.pointWonBy(scorer);

        if (currentSet.isFinished()) {
            handleFinishedSet(scorer);
            prepareForNextSet();
        }
    }

    private void handleFinishedSet(Player scorer) {
        int scorerSets = sets.get(scorer) + 1;
        sets.set(scorer, scorerSets);

        if (scorerSets == SETS_TO_WIN_MATCH) {
            winner = scorer;
        }
    }

    private void prepareForNextSet() {
        currentSet = new TennisSet(firstPlayer, secondPlayer);
    }
}