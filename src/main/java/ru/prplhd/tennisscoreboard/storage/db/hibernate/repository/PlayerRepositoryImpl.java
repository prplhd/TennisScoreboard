package ru.prplhd.tennisscoreboard.storage.db.hibernate.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.prplhd.tennisscoreboard.repository.PlayerRepository;
import ru.prplhd.tennisscoreboard.storage.db.hibernate.entity.PlayerEntity;

import java.util.Optional;

public class PlayerRepositoryImpl implements PlayerRepository {

    private static final Class<PlayerEntity> clazz = PlayerEntity.class;
    private final SessionFactory sessionFactory;

    public PlayerRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Optional<PlayerEntity> findPlayerByName(String name) {
        Session session = sessionFactory.getCurrentSession();
        String hql = """
                SELECT p FROM PlayerEntity p
                WHERE p.name = :name
                """;
        return session.createQuery(hql, clazz).setParameter("name", name).uniqueResultOptional();
    }

    @Override
    public Optional<PlayerEntity> findById(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        return Optional.ofNullable(session.find(clazz, id));
    }

    @Override
    public PlayerEntity save(PlayerEntity entity) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(entity);
        return entity;
    }
}