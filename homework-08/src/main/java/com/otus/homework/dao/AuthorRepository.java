package com.otus.homework.dao;

import com.otus.homework.domain.Author;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public interface AuthorRepository extends MongoRepository<Author, String> {

    long count();

    Optional<Author> findById(String id);

    void deleteById(String id);

    List<Author> findAll();
}
