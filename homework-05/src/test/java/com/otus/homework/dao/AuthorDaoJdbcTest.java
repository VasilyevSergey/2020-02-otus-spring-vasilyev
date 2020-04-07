package com.otus.homework.dao;

import com.otus.homework.domain.Author;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataAccessException;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Dao для работы с авторами должен ")
@JdbcTest
@Import(AuthorDaoJdbc.class)
class AuthorDaoJdbcTest {

    private static final int EXPECTED_AUTHORS_COUNT = 2;
    private static final String EXPECTED_FIRST_AUTHOR_NAME = "Pushkin";
    private static final String INSERTED_AUTHOR_NAME = "NewAuthor";
    private static final long INSERTED_AUTHOR_ID = 3L;
    private static final long DELETE_AUTHOR_ID = 2L;
    private static final long EXPECTED_FIRST_AUTHOR_ID = 1L;
    private static final long EXPECTED_SECOND_AUTHOR_ID = 2L;
    private static final String EXPECTED_SECOND_AUTHOR_NAME = "Tolkien";

    @Autowired
    private AuthorDaoJdbc dao;

    @DisplayName("возвращать ожидаемое количество авторов")
    @Test
    void shouldReturnExpectedAuthorCount() {

        int count = dao.count();
        assertThat(count).isEqualTo(EXPECTED_AUTHORS_COUNT);
    }

    @DisplayName("возвращать заданного автора по его Id")
    @Test
    void shouldReturnExpectedAuthorById() {
        Author expectedAuthor = new Author(EXPECTED_FIRST_AUTHOR_ID, EXPECTED_FIRST_AUTHOR_NAME);
        Author actualAuthor = dao.getById(EXPECTED_FIRST_AUTHOR_ID);
        assertThat(actualAuthor).isEqualTo(expectedAuthor);
    }

    @DisplayName(" добавлять автора")
    @Test
    void shouldInsertAuthor() {
        Author expectedAuthor = new Author(INSERTED_AUTHOR_ID, INSERTED_AUTHOR_NAME);
        dao.insert(expectedAuthor);
        Author actualAuthor = dao.getById(INSERTED_AUTHOR_ID);
        assertThat(actualAuthor).isEqualTo(expectedAuthor);
    }

    @DisplayName(" удалять автора")
    @Test
    void shouldDeleteAuthor() {
        dao.deleteById(DELETE_AUTHOR_ID);
        Throwable thrown = assertThrows(DataAccessException.class, () -> {
            dao.getById(DELETE_AUTHOR_ID);
        });
        assertNotNull(thrown.getMessage());
    }

    @DisplayName(" возвращать всех авторов")
    @Test
    void shouldGetAllAuthors() {
        List<Author> expectedAuthorList = Arrays.asList(new Author(EXPECTED_FIRST_AUTHOR_ID, EXPECTED_FIRST_AUTHOR_NAME),
                new Author(EXPECTED_SECOND_AUTHOR_ID, EXPECTED_SECOND_AUTHOR_NAME));
        List<Author> actualAuthorList = dao.getAll();
        assertThat(actualAuthorList).isEqualTo(expectedAuthorList);
    }
}
