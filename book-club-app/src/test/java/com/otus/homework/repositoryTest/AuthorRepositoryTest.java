package com.otus.homework.repositoryTest;

import com.otus.homework.domain.Author;
import com.otus.homework.domain.Book;
import com.otus.homework.repository.AuthorRepository;
import com.otus.homework.repository.BookRepository;
import com.otus.homework.service.MongoUserDetailsServiceImpl;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

@DataMongoTest
@EnableConfigurationProperties
@ComponentScan({"com.otus.homework.config"/*, "com.otus.homework.events"*/})
@DisplayName("Dao для работы с авторами должен ")
class AuthorRepositoryTest {
    private static final Author EXPECTED_AUTHOR = new Author("1", "Pushkin");
    private static final Author FIRST_AUTHOR = new Author("1", "Pushkin");
    private static final Author SECOND_AUTHOR = new Author("2", "Tolkien");
    private static final Author INSERTED_AUTHOR = new Author("3", "NewAuthor");

    private static final Book EXPECTED_BOOK = new Book(
            "1",
            "Ruslan and Lyudmila",
            EXPECTED_AUTHOR);

    @Autowired
    private AuthorRepository authorRepository;

//    @Autowired
//    private BookRepository bookRepository;

    @MockBean
    private MongoUserDetailsServiceImpl userDetailsService;

    @DisplayName("возвращать заданного автора по его id")
    @Test
    void shouldReturnExpectedAuthorById() {
        val optionalActualAuthor = authorRepository.findById(EXPECTED_AUTHOR.getId());
        assertThat(optionalActualAuthor).isPresent().get()
                .isEqualToComparingFieldByField(EXPECTED_AUTHOR);
    }

    @DisplayName(" добавлять автора")
    @Test
    void shouldInsertAuthor() {
        authorRepository.save(INSERTED_AUTHOR);
        Author actualAuthor = authorRepository.findById(INSERTED_AUTHOR.getId()).get();
        assertThat(actualAuthor).isEqualTo(INSERTED_AUTHOR);
    }

    @DisplayName(" возвращать всех авторов")
    @Test
    void shouldGetAllAuthors() {
        val actualAuthorList = authorRepository.findAll();
        List<Author> expectedAuthorList = Arrays.asList(FIRST_AUTHOR, SECOND_AUTHOR);
        assertThat(actualAuthorList).isEqualTo(expectedAuthorList);
    }
}
