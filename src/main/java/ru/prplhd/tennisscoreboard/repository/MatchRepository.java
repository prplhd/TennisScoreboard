package ru.prplhd.tennisscoreboard.repository;

import ru.prplhd.tennisscoreboard.storage.db.hibernate.entity.MatchEntity;

import java.util.List;

public interface MatchRepository extends Repository<Integer, MatchEntity> {
    List<MatchEntity> findMatchesByPlayerName(String name);
}