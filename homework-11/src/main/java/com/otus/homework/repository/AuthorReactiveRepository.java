package com.otus.homework.repository;

import com.otus.homework.domain.Author;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface AuthorReactiveRepository extends ReactiveMongoRepository<Author, String> {

    Mono<Author> save(Mono<Author> person);
}
