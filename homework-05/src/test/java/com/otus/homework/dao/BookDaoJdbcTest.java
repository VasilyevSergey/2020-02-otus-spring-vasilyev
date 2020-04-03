package com.otus.homework.dao;

import com.otus.homework.domain.Author;
import com.otus.homework.domain.Book;
import com.otus.homework.domain.Genre;
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

@DisplayName("Dao для работы с книгами должен ")
@JdbcTest
@Import(BookDaoJdbc.class)
class BookDaoJdbcTest {

    private static final int EXPECTED_BOOKS_COUNT = 2;

    private static final Book EXPECTED_FIRST_BOOK = new Book(
            1L,
            "Руслан и Людмила",
            new Author(1L, "Пушкин"),
            new Genre(1L, "Поэма в стихах")
    );

    private static final Book EXPECTED_SECOND_BOOK = new Book(
            2L,
            "Властелин колец",
            new Author(2L, "Толкиен"),
            new Genre(2L, "Фэнтези")
    );

    private static final Book EXPECTED_NEW_BOOK = new Book(
            3L,
            "Евгений Онегин",
            new Author(1L, "Пушкин"),
            new Genre(1L, "Поэма в стихах")
    );


    @Autowired
    private BookDaoJdbc dao;


    @DisplayName("возвращать ожидаемое количество авторов")
    @Test
    void shouldReturnExpectedBookCount() {

        int count = dao.count();
        assertThat(count).isEqualTo(EXPECTED_BOOKS_COUNT);
    }

    @DisplayName("возвращать заданную книгу по ее id")
    @Test
    void shouldReturnExpectedBookById() {
        Book actualBook = dao.getById(EXPECTED_FIRST_BOOK.getId());
        assertThat(actualBook).isEqualTo(EXPECTED_FIRST_BOOK);
    }

    @DisplayName(" добавлять книгу")
    @Test
    void shouldInsertBook() {
        dao.insert(EXPECTED_NEW_BOOK);
        Book actualBook = dao.getById(EXPECTED_NEW_BOOK.getId());
        assertThat(actualBook).isEqualTo(EXPECTED_NEW_BOOK);
    }

    @DisplayName(" удалять книгу")
    @Test
    void shouldDeleteBook() {
        dao.deleteById(EXPECTED_SECOND_BOOK.getId());
        Throwable thrown = assertThrows(DataAccessException.class, () -> {
            dao.getById(EXPECTED_SECOND_BOOK.getId());
        });
        assertNotNull(thrown.getMessage());
    }

    @DisplayName(" возвращать все книги")
    @Test
    void shouldGetAllBooks() {
        List<Book> expectedBookList = Arrays.asList(EXPECTED_FIRST_BOOK, EXPECTED_SECOND_BOOK);
        List<Book> actualBookList = dao.getAll();
        assertThat(actualBookList).isEqualTo(expectedBookList);
    }
}
