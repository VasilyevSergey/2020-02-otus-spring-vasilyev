package com.otus.homework.repository;

import com.otus.homework.domain.Book;
import com.otus.homework.domain.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public interface CommentRepository extends MongoRepository<Comment, String> {

    List<Comment> findAllByBook(@Param("templates/book") Book book);

    void deleteAllByBook(String bookId);
}
