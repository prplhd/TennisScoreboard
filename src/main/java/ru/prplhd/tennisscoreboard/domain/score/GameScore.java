package ru.prplhd.tennisscoreboard.domain.score;

import ru.prplhd.tennisscoreboard.domain.PlayerSide;

public class GameScore {

    private int firstPlayerGames = 0;
    private int secondPlayerGames = 0;

    private static final int GAMES_TO_WIN_SET = 6;

    public ScoreEffect gameWonBy(PlayerSide scorerPlayerSide) {
        addGame(scorerPlayerSide);

        int scorerGames = getGames(scorerPlayerSide);
        PlayerSide opponentPlayerSide = scorerPlayerSide.getOpponent();
        int opponentGames = getGames(opponentPlayerSide);

        if (scorerGames == GAMES_TO_WIN_SET) {
            if (opponentGames < (GAMES_TO_WIN_SET - 1)) {
                return ScoreEffect.SET_WON;
            }

            if (opponentGames == GAMES_TO_WIN_SET) {
                return ScoreEffect.TIEBREAK_STARTED;
            }
        }

        if (scorerGames == GAMES_TO_WIN_SET + 1) {
            return ScoreEffect.SET_WON;
        }

        return ScoreEffect.NONE;
    }

    public void resetGames() {
        firstPlayerGames = 0;
        secondPlayerGames = 0;
    }

    public int getFirstPlayerGames() {
        return firstPlayerGames;
    }

    public int getSecondPlayerGames() {
        return secondPlayerGames;
    }

    private int getGames(PlayerSide playerSide) {
        return switch (playerSide) {
            case FIRST -> firstPlayerGames;
            case SECOND -> secondPlayerGames;
        };
    }

    private void addGame(PlayerSide playerSide) {
        switch (playerSide) {
            case FIRST -> firstPlayerGames++;
            case SECOND -> secondPlayerGames++;
        }
    }
}