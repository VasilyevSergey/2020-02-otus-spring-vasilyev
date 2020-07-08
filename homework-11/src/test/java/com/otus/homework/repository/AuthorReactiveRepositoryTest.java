package com.otus.homework.repository;

import com.otus.homework.domain.Author;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
@EnableConfigurationProperties
@ComponentScan("com.otus.homework.testConfig")
@DisplayName("Dao для работы с авторами должен ")
class AuthorReactiveRepositoryTest {
    private static final Author EXPECTED_AUTHOR = new Author("1", "Pushkin");
    private static final Author FIRST_AUTHOR = EXPECTED_AUTHOR;
    private static final Author SECOND_AUTHOR = new Author("2", "Tolkien");
    private static final Author INSERTED_AUTHOR = new Author("3", "NewAuthor");

    @Autowired
    private AuthorReactiveRepository authorRepository;

    @DisplayName("возвращать заданного автора по его id")
    @Test
    void shouldReturnExpectedAuthorById() {
        StepVerifier
                .create(authorRepository.findById(EXPECTED_AUTHOR.getId()))
                .assertNext(author -> assertThat(author).isEqualToComparingFieldByField(EXPECTED_AUTHOR))
                .expectComplete()
                .verify();
    }

    @DisplayName(" добавлять автора")
    @Test
    void shouldInsertAuthor() {
        Mono<Author> authorMono = authorRepository.save(INSERTED_AUTHOR);

        StepVerifier
                .create(authorMono)
                .assertNext(author -> assertThat(author).isEqualTo(INSERTED_AUTHOR))
                .expectComplete()
                .verify();
    }

    @DisplayName(" возвращать всех авторов")
    @Test
    void shouldGetAllAuthors() {
        StepVerifier.create(authorRepository.findAll())
                .assertNext(author -> assertThat(author).isEqualTo(FIRST_AUTHOR))
                .assertNext(author -> assertThat(author).isEqualTo(SECOND_AUTHOR))
                .expectComplete()
                .verify();
    }
}
