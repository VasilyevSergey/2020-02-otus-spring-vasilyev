package com.otus.homework.dao;

import com.otus.homework.domain.Genre;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface GenreRepository extends MongoRepository<Genre, String> {

    long count();

    Optional<Genre> findById(String id);

    void deleteById(String id);

    List<Genre> findAll();
}
