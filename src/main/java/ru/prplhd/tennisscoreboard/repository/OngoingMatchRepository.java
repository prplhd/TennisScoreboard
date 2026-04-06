package ru.prplhd.tennisscoreboard.repository;

import ru.prplhd.tennisscoreboard.domain.Match;
import ru.prplhd.tennisscoreboard.domain.Player;

import java.util.UUID;

public interface OngoingMatchRepository {

    Match findById(UUID id);

    Match save(UUID id, Match match);

    Match delete(UUID id);

    Match applyPoint(UUID id, Player player);
}
