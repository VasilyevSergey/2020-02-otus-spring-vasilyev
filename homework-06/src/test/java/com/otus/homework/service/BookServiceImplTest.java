package com.otus.homework.service;

import com.otus.homework.dao.BookDaoJpa;
import com.otus.homework.domain.Author;
import com.otus.homework.domain.Book;
import com.otus.homework.domain.Genre;
import com.otus.homework.exception.DataLoadingException;
import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.RecoverableDataAccessException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@DisplayName("Класс BookServiceImpl должен ")
@SpringBootTest
class BookServiceImplTest {

    private static final String BOOK_ALREADY_EXIST = "Книга %s уже существует";
    private static final String BOOK_NOT_FOUND = "Книга с id '%d' не найдена";
    private static final int EXPECTED_BOOK_COUNT = 1;
    private static final long TEST_AUTHOR_ID = 1L;
    private static final String TEST_AUTHOR_NAME = "test author name";
    private static final Author TEST_AUTHOR = new Author(TEST_AUTHOR_ID, TEST_AUTHOR_NAME);

    private static final long TEST_GENRE_ID = 1L;
    private static final String TEST_GENRE_NAME = "test genre name";
    private static final Genre TEST_GENRE = new Genre(TEST_GENRE_ID, TEST_GENRE_NAME);

    private static final long TEST_BOOK_ID = 1L;
    private static final String TEST_BOOK_TITLE = "test book title";
    private static final Book TEST_BOOK = new Book(TEST_BOOK_ID, TEST_BOOK_TITLE, TEST_AUTHOR, TEST_GENRE);
    private static final Book NEW_BOOK = new Book(null, TEST_BOOK_TITLE, TEST_AUTHOR, TEST_GENRE);

    private static final String TEST_EXCEPTION_MESSAGE = "Exception message";

    @MockBean
    private BookDaoJpa bookDao;

    @MockBean
    private AuthorService authorService;

    @MockBean
    private GenreService genreService;

    @Autowired
    private BookServiceImpl bookService;

    @DisplayName("возвращать ожидаемое количество книг")
    @Test
    void shouldReturnExpectedBookCount() {
        given(bookDao.count())
                .willReturn(EXPECTED_BOOK_COUNT);
        assertThat(bookService.count()).isEqualTo(EXPECTED_BOOK_COUNT);
    }

    @SneakyThrows
    @DisplayName("возвращать заданную книгу по его id")
    @Test
    void shouldReturnExpectedBookById() {

        given(bookDao.getById(isA(Long.class)))
                .willReturn(Optional.of(TEST_BOOK));

        assertThat(bookService.getById(isA(Long.class)))
                .isEqualTo(TEST_BOOK);
    }

    @DisplayName("кидать исключение, если нельзя получить книгу по её id")
    @Test
    void shouldThrowExceptionIfCantGetBookById() {
        given(bookDao.getById(TEST_BOOK_ID))
                .willReturn(Optional.empty());

        Throwable thrown = assertThrows(DataLoadingException.class, () -> {
            bookService.getById(TEST_BOOK_ID);
        });
        assertThat(thrown.getMessage()).isEqualTo(String.format(BOOK_NOT_FOUND, TEST_AUTHOR_ID));
    }

    @SneakyThrows
    @DisplayName("добавлять книгу")
    @Test
    void shouldInsertBook() {
        given(authorService.getById(isA(Long.class)))
                .willReturn(TEST_AUTHOR);

        given(genreService.getById(isA(Long.class)))
                .willReturn(TEST_GENRE);

        bookService.insert(TEST_BOOK_TITLE, TEST_AUTHOR_ID, TEST_GENRE_ID);
        verify(bookDao, times(1)).insert(NEW_BOOK);
    }

    @SneakyThrows
    @DisplayName("кидать исключение при добавлении книги, если книга уже существует")
    @Test
    void shouldThrowExceptionWhileInsertIfBookAlreadyExist() {
        given(authorService.getById(isA(Long.class)))
                .willReturn(TEST_AUTHOR);

        given(genreService.getById(isA(Long.class)))
                .willReturn(TEST_GENRE);

        doThrow(RecoverableDataAccessException.class).when(bookDao).insert(isA(Book.class));

        Throwable thrown = assertThrows(DataLoadingException.class, () -> {
            bookService.insert(TEST_BOOK_TITLE, TEST_AUTHOR_ID, TEST_GENRE_ID);
        });
        assertThat(thrown.getMessage()).isEqualTo(String.format(BOOK_ALREADY_EXIST, NEW_BOOK.toString()));
    }

    @SneakyThrows
    @DisplayName("удалять книгу по id")
    @Test
    void shouldDeleteBookById() {
        bookService.deleteById(isA(Long.class));
        verify(bookDao, times(1)).deleteById(isA(Long.class));
    }

    @DisplayName("кидать исключение, если нельзя удалить книгу")
    @Test
    void shouldThrowExceptionIfCantDeleteBook() {
        doThrow(RecoverableDataAccessException.class).when(bookDao).deleteById(TEST_BOOK_ID);

        Throwable thrown = assertThrows(DataLoadingException.class, () -> {
            bookService.deleteById(TEST_BOOK_ID);
        });
        assertThat(thrown.getMessage()).isEqualTo(String.format(BOOK_NOT_FOUND, TEST_BOOK_ID));
    }

    @DisplayName(" возвращать все книги")
    @Test
    void shouldGetAllBooks() {
        List<Book> expectedBookList = Collections.singletonList(TEST_BOOK);

        given(bookDao.getAll())
                .willReturn(expectedBookList);

        List<Book> actualBookList = bookService.getAll();
        assertThat(actualBookList).isEqualTo(expectedBookList);
    }

    @SneakyThrows
    @DisplayName(" обновляеть книгу")
    @Test
    void shouldUpdateBook() {
        given(authorService.getById(isA(Long.class)))
                .willReturn(TEST_AUTHOR);

        given(genreService.getById(isA(Long.class)))
                .willReturn(TEST_GENRE);

        given(bookDao.update(TEST_BOOK))
                .willReturn(1);

        bookService.updateById(TEST_BOOK_ID, TEST_BOOK_TITLE, TEST_AUTHOR_ID, TEST_GENRE_ID);
        verify(bookDao, times(1)).update(TEST_BOOK);
    }

    @DisplayName("кидать исключение при обновлении книги, если книга не найдена")
    @Test
    void shouldThrowExceptionWhileUpdateIdIfBookNotFound() {
        doThrow(RecoverableDataAccessException.class).when(bookDao).update(TEST_BOOK);

        Throwable thrown = assertThrows(DataLoadingException.class, () -> {
            bookService.updateById(TEST_BOOK_ID, TEST_BOOK_TITLE, TEST_AUTHOR_ID, TEST_GENRE_ID);
        });
        assertThat(thrown.getMessage()).isEqualTo(String.format(BOOK_NOT_FOUND, TEST_BOOK_ID));
    }
}
