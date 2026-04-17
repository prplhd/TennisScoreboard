package ru.prplhd.tennisscoreboard.data.repository;

import ru.prplhd.tennisscoreboard.domain.Match;
import ru.prplhd.tennisscoreboard.domain.Player;

import java.util.Optional;
import java.util.UUID;

public interface OngoingMatchRepository {

    Optional<Match> findById(UUID uuid);

    UUID save(Match match);

    Match delete(UUID uuid);

    Match applyPoint(UUID uuid, Player player);
}
