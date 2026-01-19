package ru.prplhd.tennisscoreboard.persistence.dao;

import ru.prplhd.tennisscoreboard.persistence.entity.PlayerEntity;

import java.util.List;

public interface PlayerDao extends BaseDao<Integer, PlayerEntity> {
    @Override
    PlayerEntity save(PlayerEntity entity);

    @Override
    List<PlayerEntity> findAll();
}