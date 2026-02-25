package ru.prplhd.tennisscoreboard.storage.db.hibernate.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.prplhd.tennisscoreboard.repository.PlayerRepository;
import ru.prplhd.tennisscoreboard.storage.db.hibernate.entity.PlayerEntity;

import java.util.Optional;

public class PlayerRepositoryImpl extends BaseRepository<Integer, PlayerEntity> implements PlayerRepository {

    public PlayerRepositoryImpl(SessionFactory sessionFactory) {
        super(PlayerEntity.class, sessionFactory);
    }

    public Optional<PlayerEntity> findPlayerByName(String name) {
        Session session = sessionFactory.getCurrentSession();
        String hql = """
                SELECT p FROM PlayerEntity p
                WHERE p.name = :name
                """;
        return session.createQuery(hql, clazz).setParameter("name", name).uniqueResultOptional();
    }
}