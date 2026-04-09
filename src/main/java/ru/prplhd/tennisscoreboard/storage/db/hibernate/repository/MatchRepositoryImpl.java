package ru.prplhd.tennisscoreboard.storage.db.hibernate.repository;

import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.prplhd.tennisscoreboard.repository.MatchRepository;
import ru.prplhd.tennisscoreboard.storage.db.hibernate.entity.MatchEntity;

import java.util.List;
import java.util.Locale;

@RequiredArgsConstructor
public class MatchRepositoryImpl implements MatchRepository {

    private static final String NAME_PARAM = "name";

    private static final String FIND_ALL_MATCHES_HQL = """
            SELECT m FROM MatchEntity m
            LEFT JOIN FETCH m.firstPlayer
            LEFT JOIN FETCH m.secondPlayer
            """;

    private static final String COUNT_ALL_MATCHES_HQL = """
            SELECT COUNT (m) FROM MatchEntity m
            """;

    private static final String FILTER_BY_NAME_HQL = """
            WHERE (lower(m.firstPlayer.name) = :%s
            OR lower(m.secondPlayer.name) = :%s)
            """.formatted(NAME_PARAM, NAME_PARAM);

    private static final String DESC_ORDER_BY_ID = """
            ORDER BY m.id DESC
            """;

    private static final Class<MatchEntity> clazz = MatchEntity.class;

    private final SessionFactory sessionFactory;


    @Override
    public List<MatchEntity> findAll(int offset, int limit) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery(FIND_ALL_MATCHES_HQL + DESC_ORDER_BY_ID, clazz)
                .setMaxResults(limit)
                .setFirstResult(offset)
                .getResultList();
    }

    @Override
    public List<MatchEntity> findAllByName(int offset, int limit, String name) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery(FIND_ALL_MATCHES_HQL + FILTER_BY_NAME_HQL + DESC_ORDER_BY_ID, clazz)
                .setParameter("name", name.toLowerCase(Locale.ROOT))
                .setMaxResults(limit)
                .setFirstResult(offset)
                .getResultList();
    }

    @Override
    public void save(MatchEntity entity) {
    }

    @Override
    public long countAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery(COUNT_ALL_MATCHES_HQL, Long.class).uniqueResult();
    }

    @Override
    public long countAllByPlayerName(String name) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery(COUNT_ALL_MATCHES_HQL + FILTER_BY_NAME_HQL, Long.class).setParameter("name", name).uniqueResult();    }
}