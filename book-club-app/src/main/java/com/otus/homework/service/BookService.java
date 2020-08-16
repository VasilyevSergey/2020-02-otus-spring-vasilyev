package com.otus.homework.service;

import com.otus.homework.domain.Book;
import com.otus.homework.exception.DataLoadingException;

import java.util.List;

public interface BookService {
    long count();

    Book insert(String title, String authorId, String genreId) throws DataLoadingException;

    Book getById(String id) throws DataLoadingException;

    void deleteById(String id) throws DataLoadingException;

    List<Book> getAll();

    Book updateById(Book book) throws DataLoadingException;

    List<Book> getByAuthorId(String id) throws DataLoadingException;
}
