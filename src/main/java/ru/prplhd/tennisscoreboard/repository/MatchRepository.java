package ru.prplhd.tennisscoreboard.repository;

import ru.prplhd.tennisscoreboard.storage.db.hibernate.entity.MatchEntity;

import java.util.List;

public interface MatchRepository extends Repository<Integer, MatchEntity> {
    List<MatchEntity> findMatches(int offset, int limit);
    List<MatchEntity> findMatchesByName(int offset, int limit, String name);
    long countAll();
    long countAllByPlayerName(String name);
}