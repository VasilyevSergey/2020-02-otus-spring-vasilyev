package com.otus.homework.dao;

import com.otus.homework.domain.Author;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;


public interface AuthorRepository extends MongoRepository<Author, String> {

    long count();

    Optional<Author> findById(Long id);

    void deleteById(Long id);

    List<Author> findAll();
}