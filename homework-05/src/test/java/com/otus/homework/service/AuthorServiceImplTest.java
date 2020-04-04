package com.otus.homework.service;

import com.otus.homework.dao.AuthorDaoJdbc;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@DisplayName("Класс AuthorServiceImpl должен ")
@SpringBootTest
class AuthorServiceImplTest {

    private static final String AUTHOR_NOT_FOUND = "Автор с id '%d' не найден";
    private static final String AUTHOR_EXIST = "Автор с id '%d' уже существует";
    private static final int EXPECTED_AUTHOR_COUNT = 1;
    private static final long TEST_AUTHOR_ID = 1L;
    private static final String TEST_AUTHOR_NAME = "test author name";
    private static final Author TEST_AUTHOR = new Author(TEST_AUTHOR_ID, TEST_AUTHOR_NAME);
    private static final String TEST_EXCEPTION_MESSAGE = "Exception message";

    @MockBean
    private AuthorDaoJdbc dao;

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
        given(dao.getById(TEST_AUTHOR_ID))
                .willReturn(TEST_AUTHOR);

        assertThat(service.getById(TEST_AUTHOR_ID))
                .isEqualTo(TEST_AUTHOR);
    }

    @DisplayName("кидать исключение, если нельзя получить заданного автора по его id")
    @Test
    void shouldThrowExceptionIfCantGetAuthorById() {
        given(dao.getById(TEST_AUTHOR_ID))
                .willThrow(new RecoverableDataAccessException(TEST_EXCEPTION_MESSAGE));

        Throwable thrown = assertThrows(DataLoadingException.class, () -> {
            service.getById(TEST_AUTHOR_ID);
        });
        assertThat(thrown.getMessage()).isEqualTo(String.format(AUTHOR_NOT_FOUND, TEST_AUTHOR_ID));
    }

    @SneakyThrows
    @DisplayName("добавлять автора")
    @Test
    void shouldInsertAuthor() {
        service.insert(TEST_AUTHOR_ID, TEST_AUTHOR_NAME);
        verify(dao, times(1)).insert(TEST_AUTHOR);
    }

    @DisplayName("кидать исключение, если нельзя добавить автора")
    @Test
    void shouldThrowExceptionIfCantInsertAuthor() {
        doThrow(RecoverableDataAccessException.class).when(dao).insert(isA(Author.class));

        Throwable thrown = assertThrows(DataLoadingException.class, () -> {
            service.insert(TEST_AUTHOR_ID, TEST_AUTHOR_NAME);
        });
        assertThat(thrown.getMessage()).isEqualTo(String.format(AUTHOR_EXIST, TEST_AUTHOR_ID));
    }

    @SneakyThrows
    @DisplayName("удалять автора по id")
    @Test
    void shouldDeleteAuthorById() {
        service.deleteById(TEST_AUTHOR_ID);
        verify(dao, times(1)).deleteById(TEST_AUTHOR_ID);
    }

    @DisplayName("кидать исключение, если нельзя удалить автора")
    @Test
    void shouldThrowExceptionIfCantDeleteAuthor() {
        doThrow(RecoverableDataAccessException.class).when(dao).deleteById(isA(Long.class));

        Throwable thrown = assertThrows(DataLoadingException.class, () -> {
            service.deleteById(TEST_AUTHOR_ID);
        });
        assertThat(thrown.getMessage()).isEqualTo(String.format(AUTHOR_NOT_FOUND, TEST_AUTHOR_ID));
    }

    @DisplayName(" возвращать всех авторов")
    @Test
    void shouldGetAllAuthors() {
        List<Author> expectedAuthorList = Collections.singletonList(TEST_AUTHOR);

        given(dao.getAll())
                .willReturn(expectedAuthorList);

        List<Author> actualAuthorList = service.getAll();
        assertThat(actualAuthorList).isEqualTo(expectedAuthorList);
    }
}
