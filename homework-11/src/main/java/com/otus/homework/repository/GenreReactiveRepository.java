package com.otus.homework.repository;

import com.otus.homework.domain.Genre;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface GenreReactiveRepository extends ReactiveMongoRepository<Genre, String> {

    Mono<Long> count();

    Mono<Genre> findById(String id);

    Mono<Void> deleteById(String id);

    Flux<Genre> findAll();
}
