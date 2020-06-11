package com.otus.homework.service;

import com.otus.homework.domain.Book;
import com.otus.homework.exception.DataLoadingException;

import java.util.List;

public interface BookService {
    long count();

    void insert(String title, String authorId, String genreId) throws DataLoadingException;

    Book getById(String id) throws DataLoadingException;

    void deleteById(String id) throws DataLoadingException;

    List<Book> getAll();

    void updateById(String id, String newTitle, String newAuthorId, String newGenreId) throws DataLoadingException;
}
