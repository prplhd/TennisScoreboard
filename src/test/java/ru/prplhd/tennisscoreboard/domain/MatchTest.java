package ru.prplhd.tennisscoreboard.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MatchTest {

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
    void givenRegularGame_whenPlayerWinsPointsFromLoveToForty_thenPointValueIncreasesCorrectly() {
        assertEquals("0", match.getSnapshot().set().game().firstPlayerPoints());

        matchSteps.pointsWonBy(firstPlayer, 1);
        assertEquals("15", match.getSnapshot().set().game().firstPlayerPoints());

        matchSteps.pointsWonBy(firstPlayer, 1);
        assertEquals("30", match.getSnapshot().set().game().firstPlayerPoints());

        matchSteps.pointsWonBy(firstPlayer, 1);
        assertEquals("40", match.getSnapshot().set().game().firstPlayerPoints());
    }

    @Test
    void givenFortyLovePoints_whenLeaderWinsPoint_thenLeaderWinsGame() {
        matchSteps.pointsWonBy(firstPlayer, 3);
        assertEquals(0, match.getSnapshot().set().firstPlayerGames());

        matchSteps.pointsWonBy(firstPlayer, 1);
        assertEquals(1, match.getSnapshot().set().firstPlayerGames());
    }

    @Test
    void givenDeuce_whenPlayerWinsPoint_thenGameContinues() {
        matchSteps.pointsWonBy(firstPlayer, 3);
        matchSteps.pointsWonBy(secondPlayer, 3);
        matchSteps.pointsWonBy(firstPlayer, 1);

        assertEquals("AD", match.getSnapshot().set().game().firstPlayerPoints());
        assertEquals("40", match.getSnapshot().set().game().secondPlayerPoints());
        assertEquals(0, match.getSnapshot().set().firstPlayerGames());
    }

    @Test
    void givenFortyAdvantagePoints_whenLeaderWinsThreePoints_thenLeaderWinsGame() {
        matchSteps.pointsWonBy(firstPlayer, 3);
        matchSteps.pointsWonBy(secondPlayer, 4);

        assertEquals("40", match.getSnapshot().set().game().firstPlayerPoints());
        assertEquals("AD", match.getSnapshot().set().game().secondPlayerPoints());

        matchSteps.pointsWonBy(firstPlayer, 1);
        assertEquals("40", match.getSnapshot().set().game().firstPlayerPoints());
        assertEquals("40", match.getSnapshot().set().game().secondPlayerPoints());
        assertEquals(0, match.getSnapshot().set().firstPlayerGames());

        matchSteps.pointsWonBy(firstPlayer, 1);
        assertEquals("AD", match.getSnapshot().set().game().firstPlayerPoints());
        assertEquals("40", match.getSnapshot().set().game().secondPlayerPoints());
        assertEquals(0, match.getSnapshot().set().firstPlayerGames());

        matchSteps.pointsWonBy(firstPlayer, 1);
        assertEquals(1, match.getSnapshot().set().firstPlayerGames());
    }

    @Test
    void givenFiveLoveGames_whenLeaderWinsNextGame_thenSetEnds() {
        matchSteps.gamesWonBy(firstPlayer, 5);
        assertEquals(0, match.getSnapshot().firstPlayerSets());

        matchSteps.gamesWonBy(firstPlayer, 1);
        assertEquals(1, match.getSnapshot().firstPlayerSets());
    }

    @Test
    void givenFiveFiveGames_whenGamesBecomeSixFive_thenSetDoesNotEnd_andAtSevenFive_thenSetEnds() {
        matchSteps.gamesWonBy(firstPlayer, 5);
        matchSteps.gamesWonBy(secondPlayer, 5);
        matchSteps.gamesWonBy(firstPlayer, 1);
        assertEquals(0, match.getSnapshot().firstPlayerSets());

        matchSteps.gamesWonBy(firstPlayer, 1);
        assertEquals(1, match.getSnapshot().firstPlayerSets());
    }

    @Test
    void givenMatchStart_whenPlayerWinsTwoSetsInARow_thenPlayerWinsMatch() {
        matchSteps.setsWonBy(firstPlayer, 2);

        assertEquals(2, match.getSnapshot().firstPlayerSets());
        assertEquals(firstPlayer, match.getWinner().orElse(null));
    }

    @Test
    void givenOneOneSets_whenPlayerWinsFinalSet_thenPlayerWinsMatch() {
        matchSteps.setsWonBy(firstPlayer, 1);
        matchSteps.setsWonBy(secondPlayer, 1);

        assertEquals(1, match.getSnapshot().firstPlayerSets());
        assertEquals(1, match.getSnapshot().secondPlayerSets());
        assertTrue(match.getWinner().isEmpty());

        matchSteps.setsWonBy(firstPlayer, 1);

        assertEquals(2, match.getSnapshot().firstPlayerSets());
        assertEquals(firstPlayer, match.getWinner().orElse(null));
    }

    @Test
    void givenSetEnds_whenSetIsWon_thenGamesAndPointsReset() {
        matchSteps.gamesWonBy(firstPlayer, 5);
        matchSteps.gamesWonBy(secondPlayer, 4);
        matchSteps.pointsWonBy(firstPlayer, 3);
        matchSteps.pointsWonBy(secondPlayer, 2);

        assertEquals(5, match.getSnapshot().set().firstPlayerGames());
        assertEquals(4, match.getSnapshot().set().secondPlayerGames());
        assertEquals("40", match.getSnapshot().set().game().firstPlayerPoints());
        assertEquals("30", match.getSnapshot().set().game().secondPlayerPoints());

        matchSteps.pointsWonBy(firstPlayer, 1);

        assertEquals(0, match.getSnapshot().set().firstPlayerGames());
        assertEquals(0, match.getSnapshot().set().secondPlayerGames());
        assertEquals("0", match.getSnapshot().set().game().firstPlayerPoints());
        assertEquals("0", match.getSnapshot().set().game().secondPlayerPoints());
    }
}
