package ru.prplhd.tennisscoreboard.repository;

import java.io.Serializable;
import java.util.List;

public interface MatchRepository<ID extends Serializable, E> extends Repository<ID, E> {
    List<E> findMatchesByPlayerName(String name);
}