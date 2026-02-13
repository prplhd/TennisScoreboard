package ru.prplhd.tennisscoreboard.persistence.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.prplhd.tennisscoreboard.persistence.entity.Match;

import java.util.List;

public class MatchRepositoryImpl extends BaseRepository<Integer, Match> implements MatchRepository {

    public MatchRepositoryImpl(SessionFactory sessionFactory) {
        super(Match.class, sessionFactory);
    }

    @Override
    public List<Match> findMatchesByPlayerName(String name) {
        Session session = sessionFactory.getCurrentSession();
        String hql = """
                SELECT m FROM Match m
                JOIN FETCH m.player1
                JOIN FETCH m.player2
                WHERE (m.player1.name = :name OR m.player2.name = :name)
                """;
        return session.createQuery(hql, Match.class).setParameter("name", name).getResultList();
    }
}