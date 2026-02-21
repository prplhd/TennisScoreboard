package ru.prplhd.tennisscoreboard.storage.inmemory;

import ru.prplhd.tennisscoreboard.domain.Match;
import ru.prplhd.tennisscoreboard.repository.OngoingMatchRepository;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryOngoingMatchRepository implements OngoingMatchRepository<UUID, Match> {

    private ConcurrentHashMap<UUID, Match> ongoingMatches = new ConcurrentHashMap<>();


    @Override
    public Match findById(UUID uuid) {
        return ongoingMatches.get(uuid);
    }

    @Override
    public Match save(UUID uuid, Match match) {
        return ongoingMatches.putIfAbsent(uuid, match);
    }

    @Override
    public Match delete(UUID uuid) {
        return ongoingMatches.remove(uuid);
    }

    @Override
    public Match applyPoint(UUID uuid, Integer id) {
        Match updatedMatch = ongoingMatches.computeIfPresent(uuid, ((uuid1, match) -> {
            match.pointWonByPlayerId(id);
            return match;
        }));

        if (updatedMatch == null) {
            throw new RuntimeException();
        }

        return updatedMatch;
    }
}
