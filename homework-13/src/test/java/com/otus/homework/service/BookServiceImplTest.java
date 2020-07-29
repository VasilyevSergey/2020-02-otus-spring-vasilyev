package com.otus.homework.service;

import com.otus.homework.domain.Author;
import com.otus.homework.domain.Book;
import com.otus.homework.domain.Genre;
import com.otus.homework.exception.DataLoadingException;
import com.otus.homework.repository.BookRepository;
import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.RecoverableDataAccessException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.test.context.support.WithMockUser;

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
    private static final String BOOK_NOT_FOUND = "Книга с id '%s' не найдена";
    private static final long EXPECTED_BOOK_COUNT = 1L;
    private static final String TEST_AUTHOR_ID = "1";
    private static final String TEST_AUTHOR_NAME = "test author name";
    private static final Author TEST_AUTHOR = new Author(TEST_AUTHOR_NAME);

    private static final String TEST_GENRE_ID = "1";
    private static final String TEST_GENRE_NAME = "test genre name";
    private static final Genre TEST_GENRE = new Genre(TEST_GENRE_ID, TEST_GENRE_NAME);

    private static final String TEST_BOOK_ID = "1";
    private static final String TEST_BOOK_TITLE = "test book title";
    private static final Book TEST_BOOK = new Book(TEST_BOOK_ID, TEST_BOOK_TITLE, TEST_AUTHOR, TEST_GENRE);
    private static final Book NEW_BOOK = new Book(null, TEST_BOOK_TITLE, TEST_AUTHOR, TEST_GENRE);

    private static final String ROLE_ADMIN = "ADMIN";
    private static final String USERNAME = "username";
    private static final String ACCESS_DENIED = "Доступ запрещен";

    @MockBean
    private BookRepository bookRepository;

    @MockBean
    private AuthorService authorService;

    @MockBean
    private GenreService genreService;

    @Autowired
    private BookService bookService;

    @DisplayName("кидать исключение, если у пользователя нет роли ADMIN")
    @Test
    @WithMockUser(username = USERNAME)
    void shouldThrowAccessDeniedException() {
        Throwable thrown = assertThrows(AccessDeniedException.class, () -> {
            bookService.count();
        });
        assertThat(thrown.getMessage()).isEqualTo(ACCESS_DENIED);
    }

    @DisplayName("возвращать ожидаемое количество книг")
    @Test
    @WithMockUser(username = USERNAME, roles  = {ROLE_ADMIN})
    void shouldReturnExpectedBookCount() {
        given(bookRepository.count())
                .willReturn(EXPECTED_BOOK_COUNT);
        assertThat(bookService.count()).isEqualTo(EXPECTED_BOOK_COUNT);
    }

    @SneakyThrows
    @DisplayName("возвращать заданную книгу по его id")
    @WithMockUser(username = USERNAME, roles  = {ROLE_ADMIN})
    @Test
    void shouldReturnExpectedBookById() {

        given(bookRepository.findById(TEST_BOOK_ID))
                .willReturn(Optional.of(TEST_BOOK));

        assertThat(bookService.getById(TEST_BOOK_ID))
                .isEqualTo(TEST_BOOK);
    }

    @DisplayName("кидать исключение, если нельзя получить книгу по её id")
    @WithMockUser(username = USERNAME, roles  = {ROLE_ADMIN})
    @Test
    void shouldThrowExceptionIfCantGetBookById() {
        given(bookRepository.findById(TEST_BOOK_ID))
                .willReturn(Optional.empty());

        Throwable thrown = assertThrows(DataLoadingException.class, () -> {
            bookService.getById(TEST_BOOK_ID);
        });
        assertThat(thrown.getMessage()).isEqualTo(String.format(BOOK_NOT_FOUND, TEST_AUTHOR_ID));
    }

    @SneakyThrows
    @DisplayName("добавлять книгу")
    @WithMockUser(username = USERNAME, roles  = {ROLE_ADMIN})
    @Test
    void shouldInsertBook() {
        given(authorService.getById(isA(String.class)))
                .willReturn(TEST_AUTHOR);

        given(genreService.getById(isA(String.class)))
                .willReturn(TEST_GENRE);

        bookService.insert(TEST_BOOK_TITLE, TEST_AUTHOR_ID, TEST_GENRE_ID);
        verify(bookRepository, times(1)).save(NEW_BOOK);
    }

    @SneakyThrows
    @DisplayName("кидать исключение при добавлении книги, если книга уже существует")
    @WithMockUser(username = USERNAME, roles  = {ROLE_ADMIN})
    @Test
    void shouldThrowExceptionWhileInsertIfBookAlreadyExist() {
        given(authorService.getById(isA(String.class)))
                .willReturn(TEST_AUTHOR);

        given(genreService.getById(isA(String.class)))
                .willReturn(TEST_GENRE);

        doThrow(RecoverableDataAccessException.class).when(bookRepository).save(isA(Book.class));

        Throwable thrown = assertThrows(DataLoadingException.class, () -> {
            bookService.insert(TEST_BOOK_TITLE, TEST_AUTHOR_ID, TEST_GENRE_ID);
        });
        assertThat(thrown.getMessage()).isEqualTo(String.format(BOOK_ALREADY_EXIST, NEW_BOOK.toString()));
    }

    @SneakyThrows
    @DisplayName("удалять книгу по id")
    @WithMockUser(username = USERNAME, roles  = {ROLE_ADMIN})
    @Test
    void shouldDeleteBookById() {
        given(bookRepository.existsById(TEST_BOOK_ID))
                .willReturn(true);

        bookService.deleteById(TEST_BOOK_ID);
        verify(bookRepository, times(1)).deleteById(isA(String.class));
    }

    @DisplayName("кидать исключение, если нельзя удалить книгу")
    @WithMockUser(username = USERNAME, roles  = {ROLE_ADMIN})
    @Test
    void shouldThrowExceptionIfCantDeleteBook() {
        doThrow(RecoverableDataAccessException.class).when(bookRepository).deleteById(TEST_BOOK_ID);

        Throwable thrown = assertThrows(DataLoadingException.class, () -> {
            bookService.deleteById(TEST_BOOK_ID);
        });
        assertThat(thrown.getMessage()).isEqualTo(String.format(BOOK_NOT_FOUND, TEST_BOOK_ID));
    }

    @DisplayName(" возвращать все книги")
    @WithMockUser(username = USERNAME, roles  = {ROLE_ADMIN})
    @Test
    void shouldGetAllBooks() {
        List<Book> expectedBookList = Collections.singletonList(TEST_BOOK);

        given(bookRepository.findAll())
                .willReturn(expectedBookList);

        List<Book> actualBookList = bookService.getAll();
        assertThat(actualBookList).isEqualTo(expectedBookList);
    }

    @SneakyThrows
    @DisplayName(" обновляеть книгу")
    @WithMockUser(username = USERNAME, roles  = {ROLE_ADMIN})
    @Test
    void shouldUpdateBook() {
        given(authorService.getById(isA(String.class)))
                .willReturn(TEST_AUTHOR);

        given(genreService.getById(isA(String.class)))
                .willReturn(TEST_GENRE);

        given(bookRepository.existsById(isA(String.class)))
                .willReturn(true);

        bookService.updateById(TEST_BOOK);
        verify(bookRepository, times(1)).save(TEST_BOOK);
    }

    @DisplayName("кидать исключение при обновлении книги, если книга не найдена")
    @WithMockUser(username = USERNAME, roles  = {ROLE_ADMIN})
    @Test
    void shouldThrowExceptionWhileUpdateIdIfBookNotFound() {
        doThrow(RecoverableDataAccessException.class).when(bookRepository).save(TEST_BOOK);

        Throwable thrown = assertThrows(DataLoadingException.class, () -> {
            bookService.updateById(TEST_BOOK);
        });
        assertThat(thrown.getMessage()).isEqualTo(String.format(BOOK_NOT_FOUND, TEST_BOOK_ID));
    }
}
