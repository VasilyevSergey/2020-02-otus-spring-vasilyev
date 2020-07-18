package com.otus.homework.repository;

import com.otus.homework.domain.Comment;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface CommentReactiveRepository extends ReactiveMongoRepository<Comment, String> {

    Flux<Comment> findAllByBookId(@Param("templates/book") String bookId);

    Mono<Void> deleteAllByBookId(String bookId);
}
