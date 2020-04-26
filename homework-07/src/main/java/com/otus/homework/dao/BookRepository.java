package com.otus.homework.dao;

import com.otus.homework.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    long count();

    Optional<Book> findById(Long id);

    void deleteById(Long id);

    @Query("select b " +
            "from Book b " +
            "join fetch b.author " +
            "join fetch b.genre")
    List<Book> findAll();
}
