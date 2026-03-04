package ru.prplhd.tennisscoreboard.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MatchExceptionsTest {

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
    void givenFinishedMatch_whenPointIsAdded_thenThrowsIllegalStateException() {
        matchSteps.setsWonBy(firstPlayerId, 2);

        int firstPlayerSets = match.getScoreboard().scoreDto().firstPlayerSets();
        Player winner = match.getScoreboard().winner();

        assertEquals(2, firstPlayerSets);
        assertNotNull(winner);
        assertEquals(winner.getId(), firstPlayerId);

        assertThrows(IllegalStateException.class, () -> matchSteps.pointsWonBy(firstPlayerId, 1));
    }

    @Test
    void givenUnknownPlayerId_whenPointIsAdded_thenThrowsIllegalArgumentException() {
        Integer unknownId = 228;

        assertThrows(IllegalArgumentException.class, () -> matchSteps.pointsWonBy(unknownId, 1));
    }

    @Test
    void givenSamePlayers_whenMatchCreated_thenThrowsIllegalArgumentException() {
        Player samePlayer = new Player(3, "Melody");

        assertThrows(IllegalArgumentException.class, () -> new Match(samePlayer, samePlayer));
    }
}
