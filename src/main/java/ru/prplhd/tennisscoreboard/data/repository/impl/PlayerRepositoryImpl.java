package ru.prplhd.tennisscoreboard.data.repository.impl;

import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.prplhd.tennisscoreboard.data.repository.PlayerRepository;
import ru.prplhd.tennisscoreboard.data.entity.PlayerEntity;

import java.util.Optional;

@RequiredArgsConstructor
public class PlayerRepositoryImpl implements PlayerRepository {

    private static final String NAME_PARAM = "name";

    private static final String FIND_BY_NAME_HQL = """
        SELECT p FROM PlayerEntity p
        WHERE p.name = :""" + NAME_PARAM;

    private static final String INSERT_PLAYER_IF_ABSENT_SQL = """
        INSERT INTO players(name)
        VALUES (:%s)
        ON CONFLICT DO NOTHING""".formatted(NAME_PARAM);


    private static final Class<PlayerEntity> clazz = PlayerEntity.class;

    private final SessionFactory sessionFactory;

    @Override
    public Optional<PlayerEntity> findByName(String name) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery(FIND_BY_NAME_HQL, clazz).setParameter(NAME_PARAM, name).uniqueResultOptional();
    }

    @Override
    public Optional<PlayerEntity> findById(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        return Optional.ofNullable(session.find(clazz, id));
    }

    @Override
    public void saveIfAbsent(PlayerEntity entity) {
        Session session = sessionFactory.getCurrentSession();
        session.createNativeMutationQuery(INSERT_PLAYER_IF_ABSENT_SQL).setParameter(NAME_PARAM, entity.getName()).executeUpdate();
    }
}