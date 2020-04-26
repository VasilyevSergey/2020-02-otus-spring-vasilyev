package com.otus.homework.dao;

import com.otus.homework.domain.Book;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    long count();

    Optional<Book> findById(Long id);

    void deleteById(Long id);

    @EntityGraph(attributePaths = {"author", "genre"})
    List<Book> findAll();
}
