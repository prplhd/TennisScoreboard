package ru.prplhd.tennisscoreboard.domain;

import ru.prplhd.tennisscoreboard.domain.snapshot.SetSnapshot;
import ru.prplhd.tennisscoreboard.exception.AlreadyFinishedException;

public class TennisSet extends AbstractTennisLevel {

    private static final int GAMES_TO_WIN_SET = 6;
    private static final int GAMES_TO_WIN_EXTENDED_SET = 7;
    private static final int MIN_GAMES_LEAD_TO_WIN = 2;

    private final PlayerScores<Integer> games = new PlayerScores<>(firstPlayer, secondPlayer, 0, 0);
    private Game currentGame;

    public TennisSet(Player firstPlayer, Player secondPlayer) {
        super(firstPlayer, secondPlayer);
        this.currentGame = new RegularGame(firstPlayer, secondPlayer);
    }

    public SetSnapshot getSnapshot() {
        return new SetSnapshot(
                games.get(firstPlayer),
                games.get(secondPlayer),
                currentGame.getSnapshot()
        );
    }

    @Override
    protected void pointWonBy(Player scorer) {
        if (isFinished()) {
            throw new AlreadyFinishedException("Set already finished");
        }

        currentGame.pointWonBy(scorer);

        if (currentGame.isFinished()) {
            handleFinishedGame(scorer);
            prepareForNextGame();
        }
    }


    private void handleFinishedGame(Player scorer) {
        int scorerGames = games.get(scorer) + 1;
        games.set(scorer, scorerGames);

        if (isWin(scorer, scorerGames)) {
            winner = scorer;
        }
    }

    private boolean isWin(Player scorer, int scorerGames) {
        int opponentGames = games.get(getOpponent(scorer));
        int leadGames = scorerGames - opponentGames;

        if (scorerGames == GAMES_TO_WIN_SET && leadGames >= MIN_GAMES_LEAD_TO_WIN) {
            return true;
        }

        return scorerGames == GAMES_TO_WIN_EXTENDED_SET;
    }

    private void prepareForNextGame() {
        if (isTieBreak()) {
            currentGame = new TieBreak(firstPlayer, secondPlayer);
        } else {
            currentGame = new RegularGame(firstPlayer, secondPlayer);
        }
    }

    private boolean isTieBreak() {
        int firstPlayerGames = games.get(firstPlayer);
        int secondPlayerGames = games.get(secondPlayer);
        return (firstPlayerGames == GAMES_TO_WIN_SET) && (secondPlayerGames == GAMES_TO_WIN_SET);
    }
}