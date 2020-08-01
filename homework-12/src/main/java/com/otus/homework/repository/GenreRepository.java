package com.otus.homework.repository;

import com.otus.homework.domain.Genre;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public interface GenreRepository extends MongoRepository<Genre, String> {
}
