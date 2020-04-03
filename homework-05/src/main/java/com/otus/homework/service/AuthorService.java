package com.otus.homework.service;

import com.otus.homework.domain.Author;
import com.otus.homework.exception.DataLoadingException;

import java.util.List;

public interface AuthorService {
    int count();

    void insert(Long id, String name) throws DataLoadingException;

    Author getById(Long id) throws DataLoadingException;

    void deleteById(Long id) throws DataLoadingException;

    List<Author> getAll();

}
