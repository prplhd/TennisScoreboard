package ru.prplhd.tennisscoreboard.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class MatchTest {

    Integer firstPlayerId;
    Integer secondPlayerId;
    Match match;
    MatchSteps matchSteps;

    @BeforeEach
    void prepareMatch() {
        Player firstPlayer = new Player(1, "Alice");
        firstPlayerId = firstPlayer.getId();

        Player secondPlayer = new Player(2, "Clementine");
        secondPlayerId = secondPlayer.getId();

        match = new Match(firstPlayer, secondPlayer);
        matchSteps = new MatchSteps(match);
    }

    @Test
    void givenRegularGame_whenPlayerWinsPointsFromLoveToForty_thenPointValueIncreasesCorrectly() {
        String firstPlayerPoints = match.getScoreboard().scoreDto().firstPlayerPoints();
        assertEquals("0", firstPlayerPoints);

        matchSteps.pointsWonBy(firstPlayerId, 1);

        firstPlayerPoints = match.getScoreboard().scoreDto().firstPlayerPoints();
        assertEquals("15", firstPlayerPoints);

        matchSteps.pointsWonBy(firstPlayerId, 1);

        firstPlayerPoints = match.getScoreboard().scoreDto().firstPlayerPoints();
        assertEquals("30", firstPlayerPoints);

        matchSteps.pointsWonBy(firstPlayerId, 1);

        firstPlayerPoints = match.getScoreboard().scoreDto().firstPlayerPoints();
        assertEquals("40", firstPlayerPoints);

    }

    @Test
    void givenFortyLovePoints_whenLeaderWinsPoint_thenLeaderWinsGame() {
        matchSteps.pointsWonBy(firstPlayerId, 3);

        int firstPlayerGames = match.getScoreboard().scoreDto().firstPlayerGames();
        assertEquals(0, firstPlayerGames);

        matchSteps.pointsWonBy(firstPlayerId, 1);

        firstPlayerGames = match.getScoreboard().scoreDto().firstPlayerGames();
        assertEquals(1, firstPlayerGames);
    }

    @Test
    void givenDeuce_whenPlayerWinsPoint_thenGameContinues() {
        matchSteps.pointsWonBy(firstPlayerId, 3);
        matchSteps.pointsWonBy(secondPlayerId, 3);

        matchSteps.pointsWonBy(firstPlayerId, 1);

        String firstPlayerPoints = match.getScoreboard().scoreDto().firstPlayerPoints();
        String secondPlayerPoints = match.getScoreboard().scoreDto().secondPlayerPoints();
        int firstPlayerGames = match.getScoreboard().scoreDto().firstPlayerGames();

        assertEquals("AD", firstPlayerPoints);
        assertEquals("40", secondPlayerPoints);
        assertEquals(0, firstPlayerGames);
    }

    @Test
    void givenFortyAdvantagePoints_whenLeaderWinsThreePoints_thenLeaderWinsGame() {
        matchSteps.pointsWonBy(firstPlayerId, 3);
        matchSteps.pointsWonBy(secondPlayerId, 4);

        String firstPlayerPoints = match.getScoreboard().scoreDto().firstPlayerPoints();
        String secondPlayerPoints = match.getScoreboard().scoreDto().secondPlayerPoints();

        assertEquals("40", firstPlayerPoints);
        assertEquals("AD", secondPlayerPoints);

        matchSteps.pointsWonBy(firstPlayerId, 1);

        firstPlayerPoints = match.getScoreboard().scoreDto().firstPlayerPoints();
        secondPlayerPoints = match.getScoreboard().scoreDto().secondPlayerPoints();
        int firstPlayerGames = match.getScoreboard().scoreDto().firstPlayerGames();

        assertEquals("40", firstPlayerPoints);
        assertEquals("40", secondPlayerPoints);
        assertEquals(0, firstPlayerGames);

        matchSteps.pointsWonBy(firstPlayerId, 1);

        firstPlayerPoints = match.getScoreboard().scoreDto().firstPlayerPoints();
        secondPlayerPoints = match.getScoreboard().scoreDto().secondPlayerPoints();
        firstPlayerGames = match.getScoreboard().scoreDto().firstPlayerGames();

        assertEquals("AD", firstPlayerPoints);
        assertEquals("40", secondPlayerPoints);
        assertEquals(0, firstPlayerGames);

        matchSteps.pointsWonBy(firstPlayerId, 1);

        firstPlayerGames = match.getScoreboard().scoreDto().firstPlayerGames();
        assertEquals(1, firstPlayerGames);
    }

    @Test
    void givenFiveLoveGames_whenLeaderWinsNextGame_thenSetEnds() {
        matchSteps.gamesWonBy(firstPlayerId, 5);

        int firstPlayerSets = match.getScoreboard().scoreDto().firstPlayerSets();
        assertEquals(0, firstPlayerSets);

        matchSteps.gamesWonBy(firstPlayerId, 1);

        firstPlayerSets = match.getScoreboard().scoreDto().firstPlayerSets();
        assertEquals(1, firstPlayerSets);
    }

    @Test
    void givenFiveFiveGames_whenGamesBecomeSixFive_thenSetDoesNotEnd_andAtSevenFive_thenSetEnds() {
        matchSteps.gamesWonBy(firstPlayerId, 5);
        matchSteps.gamesWonBy(secondPlayerId, 5);
        matchSteps.gamesWonBy(firstPlayerId, 1);

        int firstPlayerSets = match.getScoreboard().scoreDto().firstPlayerSets();
        assertEquals(0, firstPlayerSets);

        matchSteps.gamesWonBy(firstPlayerId, 1);

        firstPlayerSets = match.getScoreboard().scoreDto().firstPlayerSets();
        assertEquals(1, firstPlayerSets);
    }

    @Test
    void givenMatchStart_whenPlayerWinsTwoSetsInARow_thenPlayerWinsMatch() {
        matchSteps.setsWonBy(firstPlayerId, 2);

        int firstPlayerSets = match.getScoreboard().scoreDto().firstPlayerSets();
        Player winner = match.getScoreboard().winner();

        assertEquals(2, firstPlayerSets);
        assertNotNull(winner);
        assertEquals(winner.getId(), firstPlayerId);
    }

    @Test
    void givenOneOneSets_whenPlayerWinsFinalSet_thenPlayerWinsMatch() {
        matchSteps.setsWonBy(firstPlayerId, 1);
        matchSteps.setsWonBy(secondPlayerId, 1);

        int firstPlayerSets = match.getScoreboard().scoreDto().firstPlayerSets();
        int secondPlayerSets = match.getScoreboard().scoreDto().secondPlayerSets();
        Player winner = match.getScoreboard().winner();

        assertEquals(1, firstPlayerSets);
        assertEquals(1, secondPlayerSets);
        assertNull(winner);

        matchSteps.setsWonBy(firstPlayerId, 1);

        firstPlayerSets = match.getScoreboard().scoreDto().firstPlayerSets();
        winner = match.getScoreboard().winner();

        assertEquals(2, firstPlayerSets);
        assertEquals(1, secondPlayerSets);
        assertNotNull(winner);
        assertEquals(winner.getId(), firstPlayerId);
    }

    @Test
    void givenSetEnds_whenSetIsWon_thenGamesAndPointsReset() {
        matchSteps.gamesWonBy(firstPlayerId, 5);
        matchSteps.gamesWonBy(secondPlayerId, 4);
        matchSteps.pointsWonBy(firstPlayerId, 3);
        matchSteps.pointsWonBy(secondPlayerId, 2);

        int firstPlayerGames = match.getScoreboard().scoreDto().firstPlayerGames();
        int secondPlayerGames = match.getScoreboard().scoreDto().secondPlayerGames();
        String firstPlayerPoints = match.getScoreboard().scoreDto().firstPlayerPoints();
        String secondPlayerPoints = match.getScoreboard().scoreDto().secondPlayerPoints();

        assertEquals(5, firstPlayerGames);
        assertEquals(4, secondPlayerGames);
        assertEquals("40", firstPlayerPoints);
        assertEquals("30", secondPlayerPoints);

        matchSteps.pointsWonBy(firstPlayerId, 1);

        firstPlayerGames = match.getScoreboard().scoreDto().firstPlayerGames();
        secondPlayerGames = match.getScoreboard().scoreDto().secondPlayerGames();
        firstPlayerPoints = match.getScoreboard().scoreDto().firstPlayerPoints();
        secondPlayerPoints = match.getScoreboard().scoreDto().secondPlayerPoints();

        assertEquals(0, firstPlayerGames);
        assertEquals(0, secondPlayerGames);
        assertEquals("0", firstPlayerPoints);
        assertEquals("0", secondPlayerPoints);
    }
}