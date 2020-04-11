package com.otus.homework.dao;

import com.otus.homework.domain.Book;

import java.util.List;

public interface BookDao {

    int count();

    void insert(Book book);

    int update(Book book);

    Book getById(Long id);

    void deleteById(Long id);

    List<Book> getAll();
}
