package com.otus.homework.service;

import com.otus.homework.domain.Genre;
import com.otus.homework.exception.DataLoadingException;

import java.util.List;

public interface GenreService {
    long count();

    void insert(String name) throws DataLoadingException;

    Genre getById(Long id) throws DataLoadingException;

    void deleteById(Long id) throws DataLoadingException;

    List<Genre> getAll();
}
