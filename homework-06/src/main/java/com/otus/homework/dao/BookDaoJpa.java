package com.otus.homework.dao;

import com.otus.homework.domain.Book;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public class BookDaoJpa implements BookDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public int count() {
        return getAll().size();
    }

    @Override
    public void insert(Book book) {
        if (book.getId() <= 0) {
            em.persist(book);
        } else {
            em.merge(book);
        }
    }

    @Override
    public int update(Book book) {
        Query query = em.createQuery(
                "update Book b " +
                        "set b.title = :title, " +
                        "b.author.id = :author_id, " +
                        "b.genre.id = :genre_id " +
                        "where b.id = :id");

        query.setParameter("id", book.getId());
        query.setParameter("title", book.getTitle());
        query.setParameter("author_id", book.getAuthor().getId());
        query.setParameter("genre_id", book.getGenre().getId());
        return query.executeUpdate();
    }

    @Override
    public Optional<Book> getById(Long id) {
        return Optional.ofNullable(em.find(Book.class, id));
    }

    @Override
    public void deleteById(Long id) {
        Query query = em.createQuery("delete from Book b where b.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public List<Book> getAll() {
        return em.createQuery(
                "select b " +
                        "from Book b " +
                        "join fetch b.author " +
                        "join fetch b.genre",
                Book.class)
                .getResultList();
    }
}
