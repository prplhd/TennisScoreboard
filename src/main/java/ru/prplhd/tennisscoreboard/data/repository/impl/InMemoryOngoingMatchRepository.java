package ru.prplhd.tennisscoreboard.data.repository.impl;

import ru.prplhd.tennisscoreboard.domain.Match;
import ru.prplhd.tennisscoreboard.domain.Player;
import ru.prplhd.tennisscoreboard.exception.NotFoundException;
import ru.prplhd.tennisscoreboard.data.repository.OngoingMatchRepository;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryOngoingMatchRepository implements OngoingMatchRepository {

    private final Map<UUID, Match> ongoingMatches = new ConcurrentHashMap<>();

    @Override
    public Optional<Match> findById(UUID uuid) {
        Match match = ongoingMatches.get(uuid);

        return Optional.ofNullable(match);
    }

    @Override
    public UUID save(Match match) {
        UUID uuid = UUID.randomUUID();
        ongoingMatches.put(uuid, match);
        return uuid;
    }

    @Override
    public Match delete(UUID uuid) {
        return ongoingMatches.remove(uuid);
    }

    @Override
    public Match applyPoint(UUID uuid, Player player) {
        Match updatedMatch = ongoingMatches.computeIfPresent(uuid, ((uuid1, match) -> {
            match.pointWonBy(player);
            return match;
        }));

        if (updatedMatch == null) {
            throw new NotFoundException("This match has finished or does not exist");
        }

        return updatedMatch;
    }
}
