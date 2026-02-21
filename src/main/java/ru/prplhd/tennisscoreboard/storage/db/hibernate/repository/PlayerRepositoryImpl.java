package ru.prplhd.tennisscoreboard.storage.db.hibernate.repository;

import org.hibernate.SessionFactory;
import ru.prplhd.tennisscoreboard.repository.PlayerRepository;
import ru.prplhd.tennisscoreboard.storage.db.hibernate.entity.PlayerEntity;

public class PlayerRepositoryImpl extends BaseRepository<Integer, PlayerEntity> implements PlayerRepository<Integer, PlayerEntity> {

    public PlayerRepositoryImpl(SessionFactory sessionFactory) {
        super(PlayerEntity.class, sessionFactory);
    }
}