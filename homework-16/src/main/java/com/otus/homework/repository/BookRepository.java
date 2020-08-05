package com.otus.homework.repository;

import com.otus.homework.domain.Author;
import com.otus.homework.domain.Book;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "books", path = "books")
public interface BookRepository extends MongoRepository<Book, String> {

    @RestResource(path = "author", rel = "author")
    List<Book> findAllByAuthor(Author author);
}
