package com.otus.homework.shell;

import com.otus.homework.domain.Author;
import com.otus.homework.domain.Book;
import com.otus.homework.domain.Comment;
import com.otus.homework.domain.Genre;
import com.otus.homework.exception.DataLoadingException;
import com.otus.homework.service.CommentService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.shell.Shell;

import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@DisplayName("Класс CommentCommands должен ")
@SpringBootTest
class CommentCommandsTest {

    @MockBean
    private CommentService service;

    @Autowired
    private Shell shell;

    private static final long EXPECTED_COMMENT_COUNT = 1L;
    private static final String COMMAND_COUNT_COMMENTS = "count comments";
    private static final String COMMAND_COUNT_COMMENTS_SHORT = "cc";
    private static final String PATTERN_COUNT_COMMENTS = "Количество комментариев: %d ";

    private static final String EXPECTED_COMMENT_ID = "1";
    private static final String EXPECTED_COMMENT_TEXT = "comment_text";
    private static final LocalDateTime TEST_ZONED_DATE_TIME = LocalDateTime.now().with(ChronoField.NANO_OF_SECOND, 0);
    private static final String BOOK_ID = "1";

    private static final String TEST_AUTHOR_NAME = "test author name";
    private static final Author TEST_AUTHOR = new Author(TEST_AUTHOR_NAME);
    private static final String TEST_COMMENTATOR = "commentator";

    private static final String TEST_GENRE_ID = "1";
    private static final String TEST_GENRE_NAME = "test genre name";
    private static final Genre TEST_GENRE = new Genre(TEST_GENRE_ID, TEST_GENRE_NAME);

    private static final String TEST_BOOK_ID = "1";
    private static final String TEST_BOOK_TITLE = "test book title";
    private static final Book TEST_BOOK = new Book(TEST_BOOK_ID, TEST_BOOK_TITLE, TEST_AUTHOR, TEST_GENRE);

    private static final Comment EXPECTED_COMMENT = new Comment(
            EXPECTED_COMMENT_ID,
            EXPECTED_COMMENT_TEXT,
            TEST_BOOK,
            TEST_ZONED_DATE_TIME,
            TEST_COMMENTATOR);

    private static final String COMMAND_ADD_COMMENT = "add comment";
    private static final String COMMAND_ADD_COMMENT_SHORT = "ac";
    private static final String PATTERN_SUCCEED_ADD_COMMENT = "Комментарий '%s' добавлен";

    private static final String ERROR_INSERT = "При добавлении комментария '%s' произошла ошибка";
    private static final String COMMENT_NOT_FOUND = "Комментарий с id '%s' не найден";

    private static final String COMMAND_GET_COMMENT = "get comment";
    private static final String COMMAND_GET_COMMENT_SHORT = "gc";

    private static final String COMMAND_GET_ALL_COMMENTS = "get all comments";
    private static final String COMMAND_GET_ALL_COMMENTS_SHORT = "gac";

    private static final String COMMAND_DELETE_COMMENT = "delete comment";
    private static final String COMMAND_DELETE_COMMENT_SHORT = "dc";
    private static final String PATTERN_SUCCEED_DELETE_COMMENT = "Комментарий с id '%s' удален";

    @DisplayName("возвращать количество комментариев")
    @Test
    void shouldReturnExpectedCommentCount() {
        given(service.count()).willReturn(EXPECTED_COMMENT_COUNT);

        String result = (String) shell.evaluate(() -> COMMAND_COUNT_COMMENTS);
        assertThat(result).isEqualTo(String.format(PATTERN_COUNT_COMMENTS, EXPECTED_COMMENT_COUNT));
        verify(service, times(1)).count();

        result = (String) shell.evaluate(() -> COMMAND_COUNT_COMMENTS_SHORT);
        assertThat(result).isEqualTo(String.format(PATTERN_COUNT_COMMENTS, EXPECTED_COMMENT_COUNT));
        verify(service, times(2)).count();
    }

    @SneakyThrows
    @DisplayName("добавлять комментарий")
    @Test
    void shouldAddComment() {

        String result = (String) shell.evaluate(() -> String.format(
                "%s %s %s %s", COMMAND_ADD_COMMENT, EXPECTED_COMMENT_TEXT, BOOK_ID, TEST_COMMENTATOR));
        assertThat(result).isEqualTo(String.format(PATTERN_SUCCEED_ADD_COMMENT, EXPECTED_COMMENT_TEXT));
        verify(service, times(1)).add(EXPECTED_COMMENT_TEXT, BOOK_ID, TEST_COMMENTATOR);

        result = (String) shell.evaluate(() -> String.format(
                "%s %s %s %s", COMMAND_ADD_COMMENT_SHORT, EXPECTED_COMMENT_TEXT, BOOK_ID, TEST_COMMENTATOR));
        assertThat(result).isEqualTo(String.format(PATTERN_SUCCEED_ADD_COMMENT, EXPECTED_COMMENT_TEXT));
        verify(service, times(2)).add(EXPECTED_COMMENT_TEXT, BOOK_ID, TEST_COMMENTATOR);
    }

    @SneakyThrows
    @DisplayName(" кидать исключение, если не получилось добавить комментарий")
    @Test
    void shouldNotAddCommentIfCommentAlreadyExist() {
        Throwable throwable = new Exception();
        doThrow(new DataLoadingException(String.format(ERROR_INSERT, EXPECTED_COMMENT_TEXT), throwable))
                .when(service).add(EXPECTED_COMMENT_TEXT, BOOK_ID, TEST_COMMENTATOR);

        String result = (String) shell.evaluate(() -> String.format(
                "%s %s %s %s", COMMAND_ADD_COMMENT, EXPECTED_COMMENT_TEXT, BOOK_ID, TEST_COMMENTATOR));
        assertThat(result).isEqualTo(String.format(ERROR_INSERT, EXPECTED_COMMENT_TEXT));
        verify(service, times(1)).add(EXPECTED_COMMENT_TEXT, BOOK_ID, TEST_COMMENTATOR);

        result = (String) shell.evaluate(() -> String.format(
                "%s %s %s %s", COMMAND_ADD_COMMENT_SHORT, EXPECTED_COMMENT_TEXT, BOOK_ID, TEST_COMMENTATOR));
        assertThat(result).isEqualTo(String.format(ERROR_INSERT, EXPECTED_COMMENT_TEXT));
        verify(service, times(2)).add(EXPECTED_COMMENT_TEXT, BOOK_ID, TEST_COMMENTATOR);
    }

    @SneakyThrows
    @DisplayName("получать комментарий по id")
    @Test
    void shouldGetCommentById() {
        given(service.getById(EXPECTED_COMMENT_ID)).willReturn(EXPECTED_COMMENT);

        String result = (String) shell.evaluate(() -> String.format("%s %s", COMMAND_GET_COMMENT, EXPECTED_COMMENT_ID));
        assertThat(result).isEqualTo(EXPECTED_COMMENT.toString());
        verify(service, times(1)).getById(EXPECTED_COMMENT_ID);

        result = (String) shell.evaluate(() -> String.format("%s %s", COMMAND_GET_COMMENT_SHORT, EXPECTED_COMMENT_ID));
        assertThat(result).isEqualTo(EXPECTED_COMMENT.toString());
        verify(service, times(2)).getById(EXPECTED_COMMENT_ID);
    }

    @SneakyThrows
    @DisplayName(" не получать комментарий, если комментария не существует")
    @Test
    void shouldNotGetCommentIfCommentNotExist() {
        Throwable throwable = new Exception();
        doThrow(new DataLoadingException(String.format(COMMENT_NOT_FOUND, EXPECTED_COMMENT_ID), throwable))
                .when(service).getById(EXPECTED_COMMENT_ID);

        String result = (String) shell.evaluate(() -> String.format("%s %s", COMMAND_GET_COMMENT, EXPECTED_COMMENT_ID));
        assertThat(result).isEqualTo(String.format(COMMENT_NOT_FOUND, EXPECTED_COMMENT_ID));
        verify(service, times(1)).getById(EXPECTED_COMMENT_ID);

        result = (String) shell.evaluate(() -> String.format("%s %s", COMMAND_GET_COMMENT_SHORT, EXPECTED_COMMENT_ID));
        assertThat(result).isEqualTo(String.format(COMMENT_NOT_FOUND, EXPECTED_COMMENT_ID));
        verify(service, times(2)).getById(EXPECTED_COMMENT_ID);
    }

    @DisplayName("получать все комментарии")
    @Test
    void shouldGetAllComments() {
        given(service.getAll()).willReturn(Collections.singletonList(EXPECTED_COMMENT));
        String expectedCommentList = String.format(
                "Список комментариев:\nid = %s, комментарий = %s, комментатор = %s, дата = %s\n",
                EXPECTED_COMMENT_ID, EXPECTED_COMMENT_TEXT, TEST_COMMENTATOR, EXPECTED_COMMENT.getDatetime().toString());

        String result = (String) shell.evaluate(() -> COMMAND_GET_ALL_COMMENTS);
        assertThat(result).isEqualTo(expectedCommentList);
        verify(service, times(1)).getAll();

        result = (String) shell.evaluate(() -> COMMAND_GET_ALL_COMMENTS_SHORT);
        assertThat(result).isEqualTo(expectedCommentList);
        verify(service, times(2)).getAll();
    }

    @SneakyThrows
    @DisplayName("удалять комментарий по id")
    @Test
    void shouldDeleteCommentById() {

        String result = (String) shell.evaluate(() -> String.format("%s %s", COMMAND_DELETE_COMMENT, EXPECTED_COMMENT_ID));
        assertThat(result).isEqualTo(String.format(PATTERN_SUCCEED_DELETE_COMMENT, EXPECTED_COMMENT_ID));
        verify(service, times(1)).deleteById(EXPECTED_COMMENT_ID);

        result = (String) shell.evaluate(() -> String.format("%s %s", COMMAND_DELETE_COMMENT_SHORT, EXPECTED_COMMENT_ID));
        assertThat(result).isEqualTo(String.format(PATTERN_SUCCEED_DELETE_COMMENT, EXPECTED_COMMENT_ID));
        verify(service, times(2)).deleteById(EXPECTED_COMMENT_ID);
    }

    @SneakyThrows
    @DisplayName(" не удалять комментарий, если комментарий не существует")
    @Test
    void shouldNotDeleteCommentIfCommentDoesNotExist() {
        Throwable throwable = new Exception();
        doThrow(new DataLoadingException(String.format(COMMENT_NOT_FOUND, EXPECTED_COMMENT_ID), throwable))
                .when(service).deleteById(EXPECTED_COMMENT_ID);

        String result = (String) shell.evaluate(() -> String.format("%s %s", COMMAND_DELETE_COMMENT, EXPECTED_COMMENT_ID));
        assertThat(result).isEqualTo(String.format(COMMENT_NOT_FOUND, EXPECTED_COMMENT_ID));
        verify(service, times(1)).deleteById(EXPECTED_COMMENT_ID);

        result = (String) shell.evaluate(() -> String.format("%s %s", COMMAND_DELETE_COMMENT_SHORT, EXPECTED_COMMENT_ID));
        assertThat(result).isEqualTo(String.format(COMMENT_NOT_FOUND, EXPECTED_COMMENT_ID));
        verify(service, times(2)).deleteById(EXPECTED_COMMENT_ID);
    }
}
