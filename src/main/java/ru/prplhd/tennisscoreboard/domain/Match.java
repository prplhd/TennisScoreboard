package ru.prplhd.tennisscoreboard.domain;

import ru.prplhd.tennisscoreboard.domain.score.Score;
import ru.prplhd.tennisscoreboard.domain.score.ScoreEffect;
import ru.prplhd.tennisscoreboard.dto.match.MatchDto;

import java.util.Objects;

public class Match {

    private final Player firstPlayer;
    private final Player secondPlayer;
    private Player winner;

    private final Score score;

    private boolean isFinished;

    public Match(Player firstPlayer, Player secondPlayer) {
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
        this.score = new Score();
    }

    public ScoreEffect pointWonByPlayerId(Integer id) {
        if (isFinished) {
            throw new IllegalStateException("Match already finished");
        }

        PlayerSide playerSide;
        Player winnerCandidate;

        if (Objects.equals(firstPlayer.getId(), id)) {
            playerSide = PlayerSide.FIRST;
            winnerCandidate = firstPlayer;
        } else if (Objects.equals(secondPlayer.getId(), id)) {
            playerSide = PlayerSide.SECOND;
            winnerCandidate = secondPlayer;
        } else {
            throw new IllegalArgumentException("Unknown player id: " + id);
        }

        ScoreEffect result = score.pointWonBy(playerSide);
        if (result == ScoreEffect.MATCH_WON) {
            isFinished = true;
            winner = winnerCandidate;
        }

        return result;
    }

    public MatchDto getScoreboard() {
        String winnerName = winner == null ? null : winner.getName();
        return new MatchDto(
                firstPlayer.getName(),
                secondPlayer.getName(),
                score.getScore(),
                winnerName
        );
    }
}