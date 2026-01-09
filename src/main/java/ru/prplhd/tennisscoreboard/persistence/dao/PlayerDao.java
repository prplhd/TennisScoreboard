package ru.prplhd.tennisscoreboard.persistence.dao;

import ru.prplhd.tennisscoreboard.persistence.entity.PlayerEntity;

import java.util.List;

public interface PlayerDao extends BaseDao<Long, PlayerEntity> {
    @Override
    PlayerEntity save(PlayerEntity entity);

    @Override
    List<PlayerEntity> findAll();
}