package ru.prplhd.tennisscoreboard.persistence.dao;

import ru.prplhd.tennisscoreboard.persistence.entity.FinishedMatchEntity;

import java.util.List;

public interface MatchDao extends BaseDao<Integer, FinishedMatchEntity>{
    @Override
    List<FinishedMatchEntity> findAll();

    @Override
    FinishedMatchEntity save(FinishedMatchEntity entity);
}
