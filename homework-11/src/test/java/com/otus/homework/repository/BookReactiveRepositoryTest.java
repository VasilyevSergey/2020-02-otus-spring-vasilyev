package com.otus.homework.repository;

import com.otus.homework.domain.Author;
import com.otus.homework.domain.Book;
import com.otus.homework.domain.Genre;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import reactor.test.StepVerifier;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
@EnableConfigurationProperties
@ComponentScan("com.otus.homework.testConfig")
@DisplayName("Dao для работы с книгами должен ")
class BookReactiveRepositoryTest {

    private static final Author FIRST_AUTHOR = new Author("1", "Pushkin");
    private static final Author SECOND_AUTHOR = new Author("2", "Tolkien");

    private static final Genre POEM = new Genre("1", "A poem in verse");
    private static final Genre FANTASY = new Genre("2", "Fantasy");

    private static final Book FIRST_BOOK = new Book("1", "Ruslan and Lyudmila", FIRST_AUTHOR.getId(), POEM.getId());
    private static final Book SECOND_BOOK = new Book("2", "Lord of the Rings", SECOND_AUTHOR.getId(), FANTASY.getId());

    @Autowired
    private BookReactiveRepository bookReactiveRepository;

    @DisplayName(" возвращать всех авторов")
    @Test
    void shouldGetAllAuthors() {
        StepVerifier.create(bookReactiveRepository.findAll())
                .assertNext(author -> assertThat(author).isEqualTo(FIRST_BOOK))
                .assertNext(author -> assertThat(author).isEqualTo(SECOND_BOOK))
                .expectComplete()
                .verify();
    }
}
