package ru.prplhd.tennisscoreboard.persistence.dao;

import java.util.List;
import java.util.Optional;

public interface BaseDao<ID, T> {

    Optional<T> findById(ID id);

    List<T> findAll();

    T save(T entity);

    void delete(T entity);

    Optional<T> update(T entity);
}