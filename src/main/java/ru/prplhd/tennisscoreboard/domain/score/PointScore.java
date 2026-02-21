package ru.prplhd.tennisscoreboard.domain.score;

import ru.prplhd.tennisscoreboard.domain.PlayerSide;

public interface PointScore {

    ScoreEffect pointWonBy(PlayerSide scorerPlayerSide);

    void resetPoints();

    String getFirstPlayerPoints();

    String getSecondPlayerPoints();

    PointScoreMode getMode();
}
