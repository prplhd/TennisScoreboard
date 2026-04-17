package ru.prplhd.tennisscoreboard.data.repository;

import ru.prplhd.tennisscoreboard.data.entity.MatchEntity;

import java.util.List;

public interface MatchRepository {

    List<MatchEntity> findAll(int offset, int limit);

    List<MatchEntity> findAllByName(int offset, int limit, String name);

    void save(MatchEntity entity);

    long countAll();

    long countAllByPlayerName(String name);
}