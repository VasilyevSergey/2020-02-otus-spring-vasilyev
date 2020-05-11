package com.otus.homework.dao;

import com.otus.homework.domain.Author;
import com.otus.homework.domain.Book;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends MongoRepository<Book, String> {

    long count();

    Optional<Book> findById(String id);

    void deleteById(String id);

    List<Book> findAll();

    List<Book> findAllByAuthor(Author author);
}
