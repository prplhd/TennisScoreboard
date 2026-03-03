package ru.prplhd.tennisscoreboard.storage.db.hibernate.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.prplhd.tennisscoreboard.repository.MatchRepository;
import ru.prplhd.tennisscoreboard.storage.db.hibernate.entity.MatchEntity;

import java.util.List;

public class MatchRepositoryImpl extends BaseRepository<Integer, MatchEntity> implements MatchRepository {

    public MatchRepositoryImpl(SessionFactory sessionFactory) {
        super(MatchEntity.class, sessionFactory);
    }

    private static final String FIND_ALL_MATCHES = """
            SELECT m FROM MatchEntity m
            JOIN FETCH m.player1
            JOIN FETCH m.player2
            """;

    private static final String COUNT_ALL_MATCHES_HQL = """
            SELECT COUNT (m) FROM MatchEntity m
            """;

    private static final String FILTER_BY_NAME_HQL = """
            WHERE (lower(m.player1.name) = :name OR lower(m.player2.name) = :name)
            """;

    private static final String DESC_ORDER_BY_ID = """
            ORDER BY m.id DESC
            """;

    @Override
    public List<MatchEntity> findMatches(int offset, int limit) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery(FIND_ALL_MATCHES + DESC_ORDER_BY_ID, clazz)
                .setMaxResults(limit)
                .setFirstResult(offset)
                .getResultList();
    }

    @Override
    public List<MatchEntity> findMatchesByName(int offset, int limit, String name) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery(FIND_ALL_MATCHES + FILTER_BY_NAME_HQL + DESC_ORDER_BY_ID, clazz)
                .setParameter("name", name)
                .setMaxResults(limit)
                .setFirstResult(offset)
                .getResultList();
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