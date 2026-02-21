package ru.prplhd.tennisscoreboard.storage.db.hibernate.repository;

import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.prplhd.tennisscoreboard.repository.Repository;
import ru.prplhd.tennisscoreboard.storage.db.hibernate.entity.BaseEntity;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public abstract class BaseRepository<ID extends Serializable, E extends BaseEntity<ID>> implements Repository<ID, E> {

    protected final Class<E> clazz;
    protected final SessionFactory sessionFactory;

    @Override
    public Optional<E> findById(ID id) {
        Session session = sessionFactory.getCurrentSession();
        return Optional.ofNullable(session.find(clazz, id));
    }

    @Override
    public List<E> findAll() {
        Session session = sessionFactory.getCurrentSession();
        String entityName = clazz.getSimpleName();
        return session.createQuery("SELECT e FROM " + entityName + " e", clazz).list();
    }

    @Override
    public E save(E entity) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(entity);
        return entity;
    }

    @Override
    public void delete(E entity) {
        Session session = sessionFactory.getCurrentSession();
        session.remove(entity);
        session.flush();
    }

    @Override
    public void update(E entity) {
        Session session = sessionFactory.getCurrentSession();
        session.merge(entity);
    }
}