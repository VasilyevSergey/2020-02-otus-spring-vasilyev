package com.otus.homework.dao;

import com.otus.homework.domain.Genre;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public class GenreDaoJpa implements GenreDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public int count() {
        return getAll().size();
    }

    @Override
    public void insert(Genre genre) {
        if (genre.getId() == null) {
            em.persist(genre);
        } else {
            em.merge(genre);
        }
    }

    @Override
    public Optional<Genre> getById(Long id) {
        return Optional.ofNullable(em.find(Genre.class, id));
    }

    @Override
    public void deleteById(Long id) {
        Query query = em.createQuery("delete from Genre g where g.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public List<Genre> getAll() {
        return em.createQuery("select g from Genre g", Genre.class)
                .getResultList();
    }
}
