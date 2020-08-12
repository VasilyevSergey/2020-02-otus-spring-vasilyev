package com.otus.homework.service;

import com.otus.homework.domain.Comment;
import com.otus.homework.exception.DataLoadingException;

import java.util.List;

public interface CommentService {
    long count();

    void add(String commentText, String bookId, String commentator) throws DataLoadingException;

    void updateById(String id, String commentText) throws DataLoadingException;

    Comment getById(String id) throws DataLoadingException;

    List<Comment> getByBookId(String id) throws DataLoadingException;

    void deleteById(String id) throws DataLoadingException;

    List<Comment> getAll();
}
