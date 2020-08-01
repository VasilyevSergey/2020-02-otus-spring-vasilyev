package com.otus.homework.repository;

import com.otus.homework.domain.Author;
import com.otus.homework.domain.Book;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public interface BookRepository extends MongoRepository<Book, String> {
    List<Book> findAllByAuthor(Author author);
}
