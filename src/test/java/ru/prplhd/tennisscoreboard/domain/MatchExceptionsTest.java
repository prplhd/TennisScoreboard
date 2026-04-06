package ru.prplhd.tennisscoreboard.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.prplhd.tennisscoreboard.exception.AlreadyFinishedException;
import ru.prplhd.tennisscoreboard.exception.NotFoundException;
import ru.prplhd.tennisscoreboard.exception.SamePlayerException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MatchExceptionsTest {

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
    void givenFinishedMatch_whenPointIsAdded_thenThrowsAlreadyFinishedException() {
        matchSteps.setsWonBy(firstPlayer, 2);
        int firstPlayerSets = match.getSnapshot().firstPlayerSets();
        Optional<Player> winner = match.getWinner();

        assertEquals(2, firstPlayerSets);
        assertTrue(winner.isPresent());
        assertEquals(winner.get(), firstPlayer);

        assertThrows(AlreadyFinishedException.class, () -> matchSteps.pointsWonBy(firstPlayer, 1));
    }

    @Test
    void givenUnknownPlayer_whenPointIsAdded_thenThrowsNotFoundException() {
        Player unknownPlayer = new Player("Eva");

        assertThrows(NotFoundException.class, () -> matchSteps.pointsWonBy(unknownPlayer, 1));
    }

    @Test
    void givenSamePlayers_whenMatchCreated_thenThrowsSamePlayerException() {
        Player samePlayer = new Player("Melody");

        assertThrows(SamePlayerException.class, () -> new Match(samePlayer, samePlayer));
    }
}