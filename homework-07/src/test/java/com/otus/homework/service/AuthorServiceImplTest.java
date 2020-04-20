package com.otus.homework.service;

import com.otus.homework.dao.AuthorDao;
import com.otus.homework.domain.Author;
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

@DisplayName("Класс AuthorServiceImpl должен ")
@SpringBootTest
class AuthorServiceImplTest {

    private static final String AUTHOR_NOT_FOUND = "Автор с id '%d' не найден";
    private static final String ERROR_INSERT = "При добавлении автора '%s' произошла ошибка";
    private static final long EXPECTED_AUTHOR_COUNT = 1;
    private static final long TEST_AUTHOR_ID = 1L;
    private static final String TEST_AUTHOR_NAME = "test author name";
    private static final Author TEST_AUTHOR = new Author(TEST_AUTHOR_NAME);
    private static final long NOT_EXISTING_AUTHOR_ID = 3L;

    @MockBean
    private AuthorDao dao;

    @Autowired
    private AuthorServiceImpl service;

    @DisplayName("возвращать ожидаемое количество авторов")
    @Test
    void shouldReturnExpectedAuthorCount() {
        given(dao.count())
                .willReturn(EXPECTED_AUTHOR_COUNT);
        assertThat(service.count()).isEqualTo(EXPECTED_AUTHOR_COUNT);
    }

    @SneakyThrows
    @DisplayName("возвращать заданного автора по его id")
    @Test
    void shouldReturnExpectedAuthorById() {
        given(dao.findById(TEST_AUTHOR_ID))
                .willReturn(Optional.of(TEST_AUTHOR));

        assertThat(service.getById(TEST_AUTHOR_ID))
                .isEqualTo(TEST_AUTHOR);
    }

    @DisplayName("кидать исключение, если нельзя получить заданного автора по его id")
    @Test
    void shouldThrowExceptionIfCantGetAuthorById() {
        given(dao.findById(TEST_AUTHOR_ID))
                .willReturn(Optional.empty());

        Throwable thrown = assertThrows(DataLoadingException.class, () -> {
            service.getById(TEST_AUTHOR_ID);
        });
        assertThat(thrown.getMessage()).isEqualTo(String.format(AUTHOR_NOT_FOUND, TEST_AUTHOR_ID));
    }

    @SneakyThrows
    @DisplayName("добавлять автора")
    @Test
    void shouldInsertAuthor() {
        service.insert(TEST_AUTHOR_NAME);
        verify(dao, times(1)).save(new Author(TEST_AUTHOR_NAME));
    }

    @DisplayName("кидать исключение, если нельзя добавить автора")
    @Test
    void shouldThrowExceptionIfCantInsertAuthor() {
        doThrow(RecoverableDataAccessException.class).when(dao).save(isA(Author.class));

        Throwable thrown = assertThrows(DataLoadingException.class, () -> {
            service.insert(TEST_AUTHOR_NAME);
        });
        assertThat(thrown.getMessage()).isEqualTo(String.format(ERROR_INSERT, TEST_AUTHOR_NAME));
    }

    @SneakyThrows
    @DisplayName("удалять автора по id")
    @Test
    void shouldDeleteAuthorById() {
        given(dao.existsById(TEST_AUTHOR_ID))
                .willReturn(true);

        service.deleteById(TEST_AUTHOR_ID);
        verify(dao, times(1)).deleteById(TEST_AUTHOR_ID);
    }

    @SneakyThrows
    @DisplayName("кидать исключение при удалении автора, если автора не существует")
    @Test
    void shouldThrowExceptionIfCantDeleteAuthor() {
        given(dao.findById(NOT_EXISTING_AUTHOR_ID))
                .willReturn(Optional.empty());

        Throwable thrown = assertThrows(DataLoadingException.class, () -> {
            service.deleteById(NOT_EXISTING_AUTHOR_ID);
        });
        assertThat(thrown.getMessage()).isEqualTo(String.format(AUTHOR_NOT_FOUND, NOT_EXISTING_AUTHOR_ID));
    }

    @DisplayName(" возвращать всех авторов")
    @Test
    void shouldGetAllAuthors() {
        List<Author> expectedAuthorList = Collections.singletonList(TEST_AUTHOR);

        given(dao.findAll())
                .willReturn(expectedAuthorList);

        List<Author> actualAuthorList = service.getAll();
        assertThat(actualAuthorList).isEqualTo(expectedAuthorList);
    }
}
