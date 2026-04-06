package ru.prplhd.tennisscoreboard.domain;

public class MatchSteps {

    private final ru.prplhd.tennisscoreboard.domain.Match match;

    MatchSteps(Match match) {
        this.match = match;
    }

    void pointsWonBy(Player scorer, int count) {
        for (int i = 0; i < count; i++) {
            match.pointWonBy(scorer);
        }
    }

    void gamesWonBy(Player scorer, int count) {
        for (int i = 0; i < count; i++) {
            pointsWonBy(scorer, 4);
        }
    }

    void setsWonBy(Player scorer, int count) {
        for (int i = 0; i < count; i++) {
            gamesWonBy(scorer, 6);
        }
    }
}
