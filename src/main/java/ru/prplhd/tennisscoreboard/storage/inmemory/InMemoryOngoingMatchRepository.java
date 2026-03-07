package ru.prplhd.tennisscoreboard.storage.inmemory;

import ru.prplhd.tennisscoreboard.domain.Match;
import ru.prplhd.tennisscoreboard.exception.NotFoundException;
import ru.prplhd.tennisscoreboard.repository.OngoingMatchRepository;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryOngoingMatchRepository implements OngoingMatchRepository {

    private final Map<UUID, Match> ongoingMatches = new ConcurrentHashMap<>();

    @Override
    public Match findById(UUID uuid) {
        Match match = ongoingMatches.get(uuid);

        if (match == null) {
            throw new NotFoundException("This match has finished or does not exist");
        }

        return match;
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
            throw new NotFoundException("This match has finished or does not exist");
        }

        return updatedMatch;
    }
}
