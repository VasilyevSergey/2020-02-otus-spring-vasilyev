package com.otus.homework.repositoryTest;

import com.otus.homework.domain.Author;
import com.otus.homework.domain.Book;
import com.otus.homework.repository.BookRepository;
import com.otus.homework.service.MongoUserDetailsServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
@EnableConfigurationProperties
@ComponentScan({"com.otus.homework.config"})
@DisplayName("Dao для работы с книгами ")
class BookRepositoryTest {
    private static final Author AUTHOR = new Author("1", "Pushkin");

    private static final Book EXPECTED_BOOK = new Book(
            "1",
            "Ruslan and Lyudmila",
            AUTHOR);

    @Autowired
    private BookRepository bookRepository;

    @MockBean
    private MongoUserDetailsServiceImpl userDetailsService;

    @DisplayName("возвращать все книги автора")
    @Test
    void shouldFindAllBooksByAuthor() {
        List<Book> actualBookList = bookRepository.findAllByAuthor(AUTHOR);
        List<Book> expectedBookList = Collections.singletonList(EXPECTED_BOOK);
        assertThat(actualBookList).isEqualTo(expectedBookList);
    }
}
