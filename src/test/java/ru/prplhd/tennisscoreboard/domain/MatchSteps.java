package ru.prplhd.tennisscoreboard.domain;

public class MatchSteps {

    private final Match match;

    MatchSteps(Match match) {
        this.match = match;
    }

    void pointsWonBy(Integer id, int count) {
        for (int i = 0; i < count; i++) {
            match.pointWonByPlayerId(id);
        }
    }

    void gamesWonBy(Integer id, int count) {
        for (int i = 0; i < count; i++) {
            pointsWonBy(id, 4);
        }
    }

    void setsWonBy(Integer id, int count) {
        for (int i = 0; i < count; i++) {
            gamesWonBy(id, 6);
        }
    }
}
