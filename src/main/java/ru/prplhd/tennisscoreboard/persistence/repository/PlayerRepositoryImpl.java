package ru.prplhd.tennisscoreboard.persistence.repository;

import org.hibernate.SessionFactory;
import ru.prplhd.tennisscoreboard.persistence.entity.Player;

public class PlayerRepositoryImpl extends BaseRepository<Integer, Player> implements PlayerRepository {

    public PlayerRepositoryImpl(SessionFactory sessionFactory) {
        super(Player.class, sessionFactory);
    }
}