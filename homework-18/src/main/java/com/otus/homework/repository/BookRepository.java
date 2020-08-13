package com.otus.homework.repository;

import com.otus.homework.domain.Author;
import com.otus.homework.domain.Book;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface BookRepository extends MongoRepository<Book, String> {

    List<Book> findAllByAuthor(Author author);
}
