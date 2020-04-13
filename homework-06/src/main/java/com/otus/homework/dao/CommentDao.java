package com.otus.homework.dao;

import com.otus.homework.domain.Book;
import com.otus.homework.domain.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentDao {

    int count();

    void add(Comment comment);

    int update(Comment comment);

    Optional<Comment> getById(Long id);

    List<Comment> getByBook(Book book);

    int deleteById(Long id);

    List<Comment> getAll();
}
