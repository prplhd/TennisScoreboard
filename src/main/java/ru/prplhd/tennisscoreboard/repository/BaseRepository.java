package ru.prplhd.tennisscoreboard.repository;

import java.util.List;
import java.util.Optional;

public interface BaseRepository<ID, T> {
    Optional<T> findById(ID id);

    List<T> findAll();

    T save(T entity);

    void delete(T entity);

    Optional<T> update(T entity);
}