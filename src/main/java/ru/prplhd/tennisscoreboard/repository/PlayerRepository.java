package ru.prplhd.tennisscoreboard.repository;

import ru.prplhd.tennisscoreboard.storage.db.hibernate.entity.PlayerEntity;

import java.util.Optional;

public interface PlayerRepository {
    Optional<PlayerEntity> findByName(String name);

    Optional<PlayerEntity> findById(Integer id);

    void saveIfAbsent(PlayerEntity entity);
}