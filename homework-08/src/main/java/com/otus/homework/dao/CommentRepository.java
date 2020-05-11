package com.otus.homework.dao;

import com.otus.homework.domain.Book;
import com.otus.homework.domain.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends MongoRepository<Comment, String> {

    long count();

    Optional<Comment> findById(String id);

    List<Comment> findAllByBook(@Param("book") Book book);

    void deleteById(String id);

    List<Comment> findAll();

    void deleteAllByBook(String bookId);
}
