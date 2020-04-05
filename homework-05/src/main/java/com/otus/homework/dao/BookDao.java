package com.otus.homework.dao;

import com.otus.homework.domain.Author;
import com.otus.homework.domain.Book;
import com.otus.homework.domain.Genre;

import java.util.List;

public interface BookDao {

    int count();

    void insert(Book book);

    int updateById(Long id, String newTitle, Author newAuthor, Genre newGenre);

    Book getById(Long id);

    void deleteById(Long id);

    List<Book> getAll();
}
