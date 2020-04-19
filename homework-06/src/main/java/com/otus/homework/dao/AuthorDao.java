package com.otus.homework.dao;

import com.otus.homework.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorDao {

    int count();

    void insert(Author author);

    Optional<Author> getById(Long id);

    int deleteById(Long id);

    List<Author> getAll();
}
