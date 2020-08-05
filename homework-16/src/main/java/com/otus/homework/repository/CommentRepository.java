package com.otus.homework.repository;

import com.otus.homework.domain.Book;
import com.otus.homework.domain.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "comments", path = "comments")
public interface CommentRepository extends MongoRepository<Comment, String> {

    @RestResource(path = "book", rel = "book")
    List<Comment> findAllByBook(@Param("templates/book") Book book);

    void deleteAllByBook(String bookId);
}
