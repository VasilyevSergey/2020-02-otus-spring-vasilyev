package com.otus.homework.dao;

import com.otus.homework.domain.Author;

import java.util.List;

public interface AuthorDao {

    int count();

    void insert(Author author);

    Author getById(Long id);

    void deleteById(Long id);

    List<Author> getAll();
}
