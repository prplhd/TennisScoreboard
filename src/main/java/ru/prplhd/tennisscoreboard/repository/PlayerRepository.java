package ru.prplhd.tennisscoreboard.repository;

import java.io.Serializable;

public interface PlayerRepository<ID extends Serializable, E> extends Repository<ID, E> {
}