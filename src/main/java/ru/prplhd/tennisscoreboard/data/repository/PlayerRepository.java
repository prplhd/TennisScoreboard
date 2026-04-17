package ru.prplhd.tennisscoreboard.data.repository;

import ru.prplhd.tennisscoreboard.data.entity.PlayerEntity;

import java.util.Optional;

public interface PlayerRepository {
    Optional<PlayerEntity> findByName(String name);

    Optional<PlayerEntity> findById(Integer id);

    void saveIfAbsent(PlayerEntity entity);
}