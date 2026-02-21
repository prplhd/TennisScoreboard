package ru.prplhd.tennisscoreboard.domain.score;

import ru.prplhd.tennisscoreboard.domain.PlayerSide;
import ru.prplhd.tennisscoreboard.dto.match.ScoreDto;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

public class Score {
    private final DefaultPointScore defaultPointScore = new DefaultPointScore();
    private final TiebreakPointScore tiebreakPointScore = new TiebreakPointScore();
    private PointScore pointScore = defaultPointScore;
    private final GameScore gameScore = new GameScore();
    private final SetScore setScore = new SetScore();

    private final List<EnumMap<PlayerSide, Integer>> finishedSets = new ArrayList<>();

    public ScoreEffect pointWonBy(PlayerSide playerSide) {
        ScoreEffect pointScoreEffect = pointScore.pointWonBy(playerSide);

        if (pointScoreEffect == ScoreEffect.GAME_WON) {
            pointScore.resetPoints();

            switchToDefaultIfTiebreak();

            ScoreEffect gameScoreEffect = gameScore.gameWonBy(playerSide);
            if (gameScoreEffect == ScoreEffect.SET_WON) {
                saveFinishedSet();
                gameScore.resetGames();

                ScoreEffect setScoreEffect = setScore.setWonBy(playerSide);
                if (setScoreEffect == ScoreEffect.MATCH_WON) {
                    return ScoreEffect.MATCH_WON;
                }
            }

            switchToTiebreakIfStarted(gameScoreEffect);
        }

        return ScoreEffect.NONE;
    }

    public List<EnumMap<PlayerSide, Integer>> getFinishedSets() {
        List<EnumMap<PlayerSide, Integer>> copy = new ArrayList<>(finishedSets.size());
        for (EnumMap<PlayerSide, Integer> set : finishedSets) {
            copy.add(new EnumMap<>(set));
        }

        return copy;
    }

    public ScoreDto getScore() {
        return new ScoreDto(
                pointScore.getFirstPlayerPoints(),
                pointScore.getSecondPlayerPoints(),
                gameScore.getFirstPlayerGames(),
                gameScore.getSecondPlayerGames(),
                setScore.getFirstPlayerSets(),
                setScore.getSecondPlayerSets()
        );
    }

    private void saveFinishedSet() {
        EnumMap<PlayerSide, Integer> finishedSet = new EnumMap<>(PlayerSide.class);
        finishedSet.put(PlayerSide.FIRST, gameScore.getFirstPlayerGames());
        finishedSet.put(PlayerSide.SECOND, gameScore.getSecondPlayerGames());

        finishedSets.add(finishedSet);
    }

    private void switchToDefaultIfTiebreak() {
        if (pointScore.getMode() == PointScoreMode.TIEBREAK) {
            pointScore = defaultPointScore;
        }
    }

    private void switchToTiebreakIfStarted(ScoreEffect gameScoreEffect) {
        if (gameScoreEffect == ScoreEffect.TIEBREAK_STARTED) {
            pointScore = tiebreakPointScore;
        }
    }
}