package com.otus.homework.dao;

import com.otus.homework.domain.Author;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public class AuthorDaoJpa implements AuthorDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public int count() {
        return getAll().size();
    }

    @Override
    public void insert(Author author) {
        if (author.getId() == null) {
            em.persist(author);
        } else {
            em.merge(author);
        }
    }

    @Override
    public Optional<Author> getById(Long id) {
        return Optional.ofNullable(em.find(Author.class, id));
    }

    @Override
    public int deleteById(Long id) {
        Query query = em.createQuery("delete from Author a where a.id = :id");
        query.setParameter("id", id);
        return query.executeUpdate();
    }

    @Override
    public List<Author> getAll() {
        return em.createQuery("select a from Author a", Author.class)
                .getResultList();
    }
}
