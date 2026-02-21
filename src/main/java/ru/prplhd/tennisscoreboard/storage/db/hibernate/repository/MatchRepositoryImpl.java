package ru.prplhd.tennisscoreboard.storage.db.hibernate.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.prplhd.tennisscoreboard.repository.MatchRepository;
import ru.prplhd.tennisscoreboard.storage.db.hibernate.entity.MatchEntity;

import java.util.List;

public class MatchRepositoryImpl extends BaseRepository<Integer, MatchEntity> implements MatchRepository<Integer, MatchEntity> {

    public MatchRepositoryImpl(SessionFactory sessionFactory) {
        super(MatchEntity.class, sessionFactory);
    }

    @Override
    public List<MatchEntity> findMatchesByPlayerName(String name) {
        Session session = sessionFactory.getCurrentSession();
        String hql = """
                SELECT m FROM MatchEntity m
                JOIN FETCH m.player1
                JOIN FETCH m.player2
                WHERE (m.player1.name = :name OR m.player2.name = :name)
                """;
        return session.createQuery(hql, MatchEntity.class).setParameter("name", name).getResultList();
    }
}