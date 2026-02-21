package ru.prplhd.tennisscoreboard.repository;

import ru.prplhd.tennisscoreboard.storage.db.hibernate.entity.MatchEntity;

import java.io.Serializable;
import java.util.List;

public interface MatchRepository<ID extends Serializable, E> extends Repository<ID, E> {
    List<MatchEntity> findMatchesByPlayerName(String name);
}