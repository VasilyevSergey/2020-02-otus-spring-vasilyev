package com.otus.homework.dao;

import com.otus.homework.domain.Book;
import com.otus.homework.domain.Comment;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public class CommentDaoJpa implements CommentDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public int count() {
        return getAll().size();
    }

    @Override
    public void add(Comment comment) {
        try {
            if (comment.getId() == null) {
                em.persist(comment);
            } else {
                em.merge(comment);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    @Override
    public int update(Comment comment) {
        Query query = em.createQuery(
                "update Comment c " +
                        "set c.comment = :comment, " +
                        "c.datetime = :datetime " +
                        "where c.id = :id");

        query.setParameter("id", comment.getId());
        query.setParameter("comment", comment.getComment());
        query.setParameter("datetime", comment.getDatetime());
        return query.executeUpdate();
    }

    @Override
    public Optional<Comment> getById(Long id) {
        return Optional.ofNullable(em.find(Comment.class, id));
    }

    @Override
    public List<Comment> getByBook(Book book) {
        Query query = em.createQuery(
                "select c " +
                        "from Comment c " +
                        "join fetch c.book b " +
                        "join fetch b.author " +
                        "join fetch b.genre " +
                        "where c.book.id = :book_id"/*,
                Comment.class*/);

        query.setParameter("book_id", book.getId());

        return query.getResultList();
    }

    @Override
    public int deleteById(Long id) {
        Query query = em.createQuery("delete from Comment c where c.id = :id");
        query.setParameter("id", id);
        return query.executeUpdate();
    }

    @Override
    public List<Comment> getAll() {
        return em.createQuery(
                "select c " +
                        "from Comment c " +
                        "join fetch c.book b " +
                        "join fetch b.author " +
                        "join fetch b.genre", Comment.class)
                .getResultList();
    }
}
