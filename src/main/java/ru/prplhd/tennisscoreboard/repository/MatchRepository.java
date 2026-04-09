package ru.prplhd.tennisscoreboard.repository;

import ru.prplhd.tennisscoreboard.storage.db.hibernate.entity.MatchEntity;

import java.util.List;

public interface MatchRepository {

    List<MatchEntity> findAll(int offset, int limit);

    List<MatchEntity> findAllByName(int offset, int limit, String name);

    void save(MatchEntity entity);

    long countAll();

    long countAllByPlayerName(String name);
}