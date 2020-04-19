package com.otus.homework.dao;

import com.otus.homework.domain.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreDao {

    int count();

    void insert(Genre genre);

    Optional<Genre> getById(Long id);

    void deleteById(Long id);

    List<Genre> getAll();
}
