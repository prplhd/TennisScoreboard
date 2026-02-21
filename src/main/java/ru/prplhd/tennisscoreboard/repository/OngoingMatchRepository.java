package ru.prplhd.tennisscoreboard.repository;

import java.io.Serializable;

public interface OngoingMatchRepository<ID extends Serializable, M> {

    M findById(ID id);

    M save(ID id, M match);

    M delete(ID id);

    M applyPoint(ID id, Integer playerId);
}
