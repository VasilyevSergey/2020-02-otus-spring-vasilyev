package com.otus.homework.dao;

import com.otus.homework.domain.Genre;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface GenreDao extends CrudRepository<Genre, Long> {

    long count();

    Genre save(Genre genre);

    Optional<Genre> findById(Long id);

    void deleteById(Long id);

    List<Genre> findAll();
}
