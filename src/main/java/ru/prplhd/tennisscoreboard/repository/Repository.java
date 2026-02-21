package ru.prplhd.tennisscoreboard.repository;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface Repository<ID extends Serializable, E> {

    Optional<E> findById(ID id);

    List<E> findAll();

    E save(E entity);

    void delete(E entity);

    void update(E entity);
}