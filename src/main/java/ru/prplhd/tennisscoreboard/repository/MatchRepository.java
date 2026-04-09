package ru.prplhd.tennisscoreboard.repository;

import ru.prplhd.tennisscoreboard.storage.db.hibernate.entity.MatchEntity;

import java.util.List;

public interface MatchRepository {

    List<MatchEntity> findMatches(int offset, int limit);

    List<MatchEntity> findMatchesByName(int offset, int limit, String name);

    MatchEntity save(MatchEntity entity);

    long countAll();

    long countAllByPlayerName(String name);
}