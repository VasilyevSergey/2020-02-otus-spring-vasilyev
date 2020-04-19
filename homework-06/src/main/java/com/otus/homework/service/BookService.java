package com.otus.homework.service;

import com.otus.homework.domain.Book;
import com.otus.homework.exception.DataLoadingException;

import java.util.List;

public interface BookService {
    int count();

    void insert(String title, Long authorId, Long genreId) throws DataLoadingException;

    Book getById(Long id) throws DataLoadingException;

    void deleteById(Long id) throws DataLoadingException;

    List<Book> getAll();

    void updateById(Long id, String newTitle, Long newAuthorId, Long newGenreId) throws DataLoadingException;
}
