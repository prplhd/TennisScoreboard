package ru.prplhd.tennisscoreboard.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MatchTieBreakTest {

    Player firstPlayer;
    Player secondPlayer;
    Match match;
    MatchSteps matchSteps;

    @BeforeEach
    void prepareMatch() {
        firstPlayer = new Player("Alice");
        secondPlayer = new Player("Clementine");
        match = new Match(firstPlayer, secondPlayer);
        matchSteps = new MatchSteps(match);
    }

    @Test
    void givenTieBreak_whenPlayerWinsThreePoints_thenTieBreakPointsIncreaseByOneEachTime() {
        reachTieBreak();

        matchSteps.pointsWonBy(firstPlayer, 1);
        assertEquals("1", match.getSnapshot().set().game().firstPlayerPoints());

        matchSteps.pointsWonBy(firstPlayer, 1);
        assertEquals("2", match.getSnapshot().set().game().firstPlayerPoints());

        matchSteps.pointsWonBy(firstPlayer, 1);
        assertEquals("3", match.getSnapshot().set().game().firstPlayerPoints());
    }

    @Test
    void givenTieBreak_whenPlayerWinsFourTieBreakPoints_thenSetDoesNotEnd() {
        reachTieBreak();

        matchSteps.pointsWonBy(firstPlayer, 4);

        assertEquals(0, match.getSnapshot().firstPlayerSets());
    }

    @Test
    void givenTieBreak_whenPlayerWinsSevenTieBreakPoints_thenSetEnds() {
        reachTieBreak();

        matchSteps.pointsWonBy(firstPlayer, 7);

        assertEquals(1, match.getSnapshot().firstPlayerSets());
    }

    @Test
    void givenTieBreak_whenTieBreakPointsBecomeSixSeven_thenSetDoesNotEnd_andAtSixEight_thenSetEnds() {
        reachTieBreak();

        matchSteps.pointsWonBy(firstPlayer, 6);
        matchSteps.pointsWonBy(secondPlayer, 7);
        assertEquals(0, match.getSnapshot().secondPlayerSets());

        matchSteps.pointsWonBy(secondPlayer, 1);
        assertEquals(1, match.getSnapshot().secondPlayerSets());
    }

    @Test
    void givenTieBreak_whenSetEnds_thenGamesAndPointsReset_andNextGameIsRegular() {
        reachTieBreak();
        matchSteps.pointsWonBy(firstPlayer, 6);
        matchSteps.pointsWonBy(secondPlayer, 3);

        assertEquals(6, match.getSnapshot().set().firstPlayerGames());
        assertEquals(6, match.getSnapshot().set().secondPlayerGames());
        assertEquals("6", match.getSnapshot().set().game().firstPlayerPoints());
        assertEquals("3", match.getSnapshot().set().game().secondPlayerPoints());

        matchSteps.pointsWonBy(firstPlayer, 1);

        assertEquals(0, match.getSnapshot().set().firstPlayerGames());
        assertEquals(0, match.getSnapshot().set().secondPlayerGames());
        assertEquals("0", match.getSnapshot().set().game().firstPlayerPoints());
        assertEquals("0", match.getSnapshot().set().game().secondPlayerPoints());

        matchSteps.pointsWonBy(firstPlayer, 2);
        matchSteps.pointsWonBy(secondPlayer, 1);

        assertEquals("30", match.getSnapshot().set().game().firstPlayerPoints());
        assertEquals("15", match.getSnapshot().set().game().secondPlayerPoints());
    }

    private void reachTieBreak() {
        matchSteps.gamesWonBy(firstPlayer, 5);
        matchSteps.gamesWonBy(secondPlayer, 6);
        matchSteps.gamesWonBy(firstPlayer, 1);
    }
}