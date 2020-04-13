package com.otus.homework.service;

import com.otus.homework.domain.Comment;
import com.otus.homework.exception.DataLoadingException;

import java.util.List;

public interface CommentService {
    int count();

    void add(String commentText, Long bookId, String commentator) throws DataLoadingException;

    void updateById(Long id, String commentText) throws DataLoadingException;

    Comment getById(Long id) throws DataLoadingException;

    List<Comment> getByBookId(Long id) throws DataLoadingException;

    void deleteById(Long id) throws DataLoadingException;

    List<Comment> getAll();
}
