package com.otus.homework.dao;

import com.otus.homework.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookDao {

    int count();

    void insert(Book book);

    int update(Book book);

    Optional<Book> getById(Long id);

    void deleteById(Long id);

    List<Book> getAll();
}
