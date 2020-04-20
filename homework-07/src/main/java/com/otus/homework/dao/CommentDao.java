package com.otus.homework.dao;

import com.otus.homework.domain.Book;
import com.otus.homework.domain.Comment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CommentDao extends CrudRepository<Comment, Long> {

    long count();

    Comment save(Comment comment);

    Optional<Comment> findById(Long id);

    @Query("select c " +
            "from Comment c " +
            "join fetch c.book b " +
            "join fetch b.author " +
            "join fetch b.genre " +
            "where c.book = :book")
    List<Comment> findAllByBook(@Param("book") Book book);

    void deleteById(Long id);

    @Query("select c " +
            "from Comment c " +
            "join fetch c.book b " +
            "join fetch b.author " +
            "join fetch b.genre")
    List<Comment> findAll();
}
