package com.otus.homework.dao;

import com.otus.homework.domain.Author;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface AuthorDao extends CrudRepository<Author, Long> {

    long count();

    Author save(Author author);

    Optional<Author> findById(Long id);

    void deleteById(Long id);

    List<Author> findAll();
}
