package com.otus.homework.shell;

import com.otus.homework.domain.Author;
import com.otus.homework.exception.DataLoadingException;
import com.otus.homework.service.AuthorService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.shell.Shell;
import org.springframework.shell.standard.ShellMethod;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@DisplayName("Класс AuthorCommands должен ")
@SpringBootTest
public class AuthorCommandsTest {

    @MockBean
    private AuthorService service;

    @Autowired
    private Shell shell;

    private static final int EXPECTED_AUTHOR_COUNT = 1;
    private static final String COMMAND_COUNT_AUTHORS = "count authors";
    private static final String COMMAND_COUNT_AUTHORS_SHORT = "ca";
    private static final String PATTERN_COUNT_AUTHORS = "Количество авторов: %d ";

    private static final Long EXPECTED_AUTHOR_ID = 1L;
    private static final String EXPECTED_AUTHOR_NAME = "Name";
    private static final Author EXPECTED_AUTHOR = new Author(EXPECTED_AUTHOR_ID, EXPECTED_AUTHOR_NAME);

    private static final String COMMAND_INSERT_AUTHOR = "insert author";
    private static final String COMMAND_INSERT_AUTHOR_SHORT = "ia";
    private static final String PATTERN_SUCCEED_INSERT_AUTHOR = "Автор '%s' добавлен";

    private static final String ERROR_INSERT = "При добавлении автора '%s' произошла ошибка";
    private static final String AUTHOR_NOT_FOUND = "Автор с id '%d' не найден";

    private static final String COMMAND_GET_AUTHOR = "get author";
    private static final String COMMAND_GET_AUTHOR_SHORT = "ga";

    private static final String COMMAND_GET_ALL_AUTHORS = "get all authors";
    private static final String COMMAND_GET_ALL_AUTHORS_SHORT = "gaa";

    private static final String COMMAND_DELETE_AUTHOR = "delete author";
    private static final String COMMAND_DELETE_AUTHOR_SHORT = "da";
    private static final String PATTERN_SUCCEED_DELETE_AUTHOR = "Автор с id '%d' удален";

    @DisplayName("возвращать количество авторов")
    @Test
    void shouldReturnExpectedAuthorCount() {
        given(service.count()).willReturn(EXPECTED_AUTHOR_COUNT);

        String result = (String) shell.evaluate(() -> COMMAND_COUNT_AUTHORS);
        assertThat(result).isEqualTo(String.format(PATTERN_COUNT_AUTHORS, EXPECTED_AUTHOR_COUNT));
        verify(service, times(1)).count();

        result = (String) shell.evaluate(() -> COMMAND_COUNT_AUTHORS_SHORT);
        assertThat(result).isEqualTo(String.format(PATTERN_COUNT_AUTHORS, EXPECTED_AUTHOR_COUNT));
        verify(service, times(2)).count();
    }

    @SneakyThrows
    @DisplayName("добавлять автора")
    @Test
    void shouldInsertAuthor() {

        String result = (String) shell.evaluate(() -> String.format("%s %s", COMMAND_INSERT_AUTHOR, EXPECTED_AUTHOR_NAME));
        assertThat(result).isEqualTo(String.format(PATTERN_SUCCEED_INSERT_AUTHOR, EXPECTED_AUTHOR_NAME));
        verify(service, times(1)).insert(EXPECTED_AUTHOR_NAME);

        result = (String) shell.evaluate(() -> String.format("%s %s", COMMAND_INSERT_AUTHOR_SHORT, EXPECTED_AUTHOR_NAME));
        assertThat(result).isEqualTo(String.format(PATTERN_SUCCEED_INSERT_AUTHOR, EXPECTED_AUTHOR_NAME));
        verify(service, times(2)).insert(EXPECTED_AUTHOR_NAME);
    }

    @SneakyThrows
    @DisplayName(" кидать исключение, если не получилось добавить автора")
    @Test
    void shouldNotInsertAuthorIfAuthorAlreadyExist() {
        Throwable throwable = new Exception();
        doThrow(new DataLoadingException(String.format(ERROR_INSERT, EXPECTED_AUTHOR_NAME), throwable))
                .when(service).insert(EXPECTED_AUTHOR_NAME);

        String result = (String) shell.evaluate(() -> String.format("%s %s", COMMAND_INSERT_AUTHOR, EXPECTED_AUTHOR_NAME));
        assertThat(result).isEqualTo(String.format(ERROR_INSERT, EXPECTED_AUTHOR_NAME));
        verify(service, times(1)).insert(EXPECTED_AUTHOR_NAME);

        result = (String) shell.evaluate(() -> String.format("%s %s", COMMAND_INSERT_AUTHOR_SHORT, EXPECTED_AUTHOR_NAME));
        assertThat(result).isEqualTo(String.format(ERROR_INSERT, EXPECTED_AUTHOR_NAME));
        verify(service, times(2)).insert(EXPECTED_AUTHOR_NAME);
    }

    @SneakyThrows
    @DisplayName("получать автора по id")
    @Test
    void shouldGetAuthorById() {
        given(service.getById(EXPECTED_AUTHOR_ID)).willReturn(EXPECTED_AUTHOR);

        String result = (String) shell.evaluate(() -> String.format("%s %d", COMMAND_GET_AUTHOR, EXPECTED_AUTHOR_ID));
        assertThat(result).isEqualTo(EXPECTED_AUTHOR.toString());
        verify(service, times(1)).getById(EXPECTED_AUTHOR_ID);

        result = (String) shell.evaluate(() -> String.format("%s %d", COMMAND_GET_AUTHOR_SHORT, EXPECTED_AUTHOR_ID));
        assertThat(result).isEqualTo(EXPECTED_AUTHOR.toString());
        verify(service, times(2)).getById(EXPECTED_AUTHOR_ID);
    }

    @SneakyThrows
    @DisplayName(" не получать автора, если автора не существует")
    @Test
    void shouldNotGetAuthorIfAuthorNotExist() {
        Throwable throwable = new Exception();
        doThrow(new DataLoadingException(String.format(AUTHOR_NOT_FOUND, EXPECTED_AUTHOR_ID), throwable))
                .when(service).getById(EXPECTED_AUTHOR_ID);

        String result = (String) shell.evaluate(() -> String.format("%s %d", COMMAND_GET_AUTHOR, EXPECTED_AUTHOR_ID));
        assertThat(result).isEqualTo(String.format(AUTHOR_NOT_FOUND, EXPECTED_AUTHOR_ID));
        verify(service, times(1)).getById(EXPECTED_AUTHOR_ID);

        result = (String) shell.evaluate(() -> String.format("%s %d", COMMAND_GET_AUTHOR_SHORT, EXPECTED_AUTHOR_ID));
        assertThat(result).isEqualTo(String.format(AUTHOR_NOT_FOUND, EXPECTED_AUTHOR_ID));
        verify(service, times(2)).getById(EXPECTED_AUTHOR_ID);
    }

    @ShellMethod(value = "get all authors", key = {"gaa", "get all authors"})
    public String getAll() {
        return service.getAll().toString();
    }

    @DisplayName("получать всех авторов")
    @Test
    void shouldGetAllAuthors() {
        given(service.getAll()).willReturn(Collections.singletonList(EXPECTED_AUTHOR));
        String expectedAuthorList = String.format("Список авторов:\nid = %d, имя = %s\n", EXPECTED_AUTHOR_ID, EXPECTED_AUTHOR_NAME);

        String result = (String) shell.evaluate(() -> COMMAND_GET_ALL_AUTHORS);
        assertThat(result).isEqualTo(expectedAuthorList);
        verify(service, times(1)).getAll();

        result = (String) shell.evaluate(() -> COMMAND_GET_ALL_AUTHORS_SHORT);
        assertThat(result).isEqualTo(expectedAuthorList);
        verify(service, times(2)).getAll();
    }

    @SneakyThrows
    @DisplayName("удалять автора по id")
    @Test
    void shouldDeleteAuthorById() {

        String result = (String) shell.evaluate(() -> String.format("%s %d", COMMAND_DELETE_AUTHOR, EXPECTED_AUTHOR_ID));
        assertThat(result).isEqualTo(String.format(PATTERN_SUCCEED_DELETE_AUTHOR, EXPECTED_AUTHOR_ID));
        verify(service, times(1)).deleteById(EXPECTED_AUTHOR_ID);

        result = (String) shell.evaluate(() -> String.format("%s %d", COMMAND_DELETE_AUTHOR_SHORT, EXPECTED_AUTHOR_ID));
        assertThat(result).isEqualTo(String.format(PATTERN_SUCCEED_DELETE_AUTHOR, EXPECTED_AUTHOR_ID));
        verify(service, times(2)).deleteById(EXPECTED_AUTHOR_ID);
    }

    @SneakyThrows
    @DisplayName(" не удалять автора, если автор не существует")
    @Test
    void shouldNotDeleteAuthorIfAuthorDoesNotExist() {
        Throwable throwable = new Exception();
        doThrow(new DataLoadingException(String.format(AUTHOR_NOT_FOUND, EXPECTED_AUTHOR_ID), throwable))
                .when(service).deleteById(EXPECTED_AUTHOR_ID);

        String result = (String) shell.evaluate(() -> String.format("%s %d", COMMAND_DELETE_AUTHOR, EXPECTED_AUTHOR_ID));
        assertThat(result).isEqualTo(String.format(AUTHOR_NOT_FOUND, EXPECTED_AUTHOR_ID));
        verify(service, times(1)).deleteById(EXPECTED_AUTHOR_ID);

        result = (String) shell.evaluate(() -> String.format("%s %d", COMMAND_DELETE_AUTHOR_SHORT, EXPECTED_AUTHOR_ID));
        assertThat(result).isEqualTo(String.format(AUTHOR_NOT_FOUND, EXPECTED_AUTHOR_ID));
        verify(service, times(2)).deleteById(EXPECTED_AUTHOR_ID);
    }
}
