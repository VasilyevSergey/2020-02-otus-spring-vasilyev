package com.otus.homework.repository;

import com.otus.homework.domain.Book;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface BookReactiveRepository extends ReactiveMongoRepository<Book, String> {

    Mono<Long> count();

    Mono<Book> findById(String id);

    Mono<Void> deleteById(String id);

    Flux<Book> findAll();

    Flux<Book> findAllByAuthorId(String authorId);
}
