package ru.prplhd.tennisscoreboard.repository;

import ru.prplhd.tennisscoreboard.storage.db.hibernate.entity.PlayerEntity;

import java.util.Optional;

public interface PlayerRepository extends Repository<Integer, PlayerEntity> {
    Optional<PlayerEntity> findPlayerByName(String name);
}