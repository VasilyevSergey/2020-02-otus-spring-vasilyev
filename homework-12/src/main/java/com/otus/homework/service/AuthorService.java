package com.otus.homework.service;

import com.otus.homework.domain.Author;
import com.otus.homework.exception.DataLoadingException;

import java.util.List;

public interface AuthorService {
    long count();

    Author insert(String name) throws DataLoadingException;

    Author getById(String id) throws DataLoadingException;

    void deleteById(String id) throws DataLoadingException;

    List<Author> getAll();

    Author updateById(Author author);
}
