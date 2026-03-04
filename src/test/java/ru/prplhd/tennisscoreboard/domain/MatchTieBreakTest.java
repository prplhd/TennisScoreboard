package ru.prplhd.tennisscoreboard.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MatchTieBreakTest {

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
    void givenTieBreak_whenPlayerWinsThreePoints_thenTieBreakPointsIncreaseByOneEachTime() {
        reachTieBreak();

        matchSteps.pointsWonBy(firstPlayerId, 1);

        String firstPlayerPoints = match.getScoreboard().scoreDto().firstPlayerPoints();
        assertEquals("1", firstPlayerPoints);

        matchSteps.pointsWonBy(firstPlayerId, 1);

        firstPlayerPoints = match.getScoreboard().scoreDto().firstPlayerPoints();
        assertEquals("2", firstPlayerPoints);

        matchSteps.pointsWonBy(firstPlayerId, 1);

        firstPlayerPoints = match.getScoreboard().scoreDto().firstPlayerPoints();
        assertEquals("3", firstPlayerPoints);
    }

    @Test
    void givenTieBreak_whenPlayerWinsFourTieBreakPoints_thenSetDoesNotEnd() {
        reachTieBreak();

        matchSteps.pointsWonBy(firstPlayerId, 4);

        int firstPlayerSets = match.getScoreboard().scoreDto().firstPlayerSets();
        assertEquals(0, firstPlayerSets);
    }

    @Test
    void givenTieBreak_whenPlayerWinsSevenTieBreakPoints_thenSetEnds() {
        reachTieBreak();

        matchSteps.pointsWonBy(firstPlayerId, 7);

        int firstPlayerSets = match.getScoreboard().scoreDto().firstPlayerSets();
        assertEquals(1, firstPlayerSets);
    }

    @Test
    void givenTieBreak_whenTieBreakPointsBecomeSixSeven_thenSetDoesNotEnd_andAtSixEight_thenSetEnds() {
        reachTieBreak();

        matchSteps.pointsWonBy(firstPlayerId, 6);
        matchSteps.pointsWonBy(secondPlayerId, 7);

        int secondPlayerSets = match.getScoreboard().scoreDto().secondPlayerSets();
        assertEquals(0, secondPlayerSets);

        matchSteps.pointsWonBy(secondPlayerId, 1);

        secondPlayerSets = match.getScoreboard().scoreDto().secondPlayerSets();
        assertEquals(1, secondPlayerSets);
    }

    @Test
    void givenTieBreak_whenSetEnds_thenGamesAndPointsReset_andNextGameIsRegular() {
        reachTieBreak();
        matchSteps.pointsWonBy(firstPlayerId, 6);
        matchSteps.pointsWonBy(secondPlayerId, 3);

        int firstPlayerGames = match.getScoreboard().scoreDto().firstPlayerGames();
        int secondPlayerGames = match.getScoreboard().scoreDto().secondPlayerGames();
        String firstPlayerPoints = match.getScoreboard().scoreDto().firstPlayerPoints();
        String secondPlayerPoints = match.getScoreboard().scoreDto().secondPlayerPoints();

        assertEquals(6, firstPlayerGames);
        assertEquals(6, secondPlayerGames);
        assertEquals("6", firstPlayerPoints);
        assertEquals("3", secondPlayerPoints);

        matchSteps.pointsWonBy(firstPlayerId, 1);

        firstPlayerGames = match.getScoreboard().scoreDto().firstPlayerGames();
        secondPlayerGames = match.getScoreboard().scoreDto().secondPlayerGames();
        firstPlayerPoints = match.getScoreboard().scoreDto().firstPlayerPoints();
        secondPlayerPoints = match.getScoreboard().scoreDto().secondPlayerPoints();

        assertEquals(0, firstPlayerGames);
        assertEquals(0, secondPlayerGames);
        assertEquals("0", firstPlayerPoints);
        assertEquals("0", secondPlayerPoints);

        matchSteps.pointsWonBy(firstPlayerId, 2);
        matchSteps.pointsWonBy(secondPlayerId, 1);

        firstPlayerPoints = match.getScoreboard().scoreDto().firstPlayerPoints();
        secondPlayerPoints = match.getScoreboard().scoreDto().secondPlayerPoints();

        assertEquals("30", firstPlayerPoints);
        assertEquals("15", secondPlayerPoints);
    }

    private void reachTieBreak() {
        matchSteps.gamesWonBy(firstPlayerId, 5);
        matchSteps.gamesWonBy(secondPlayerId, 6);
        matchSteps.gamesWonBy(firstPlayerId, 1);
    }
}