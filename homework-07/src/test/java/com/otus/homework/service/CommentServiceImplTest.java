package com.otus.homework.service;

import com.otus.homework.dao.CommentDao;
import com.otus.homework.domain.Author;
import com.otus.homework.domain.Book;
import com.otus.homework.domain.Comment;
import com.otus.homework.domain.Genre;
import com.otus.homework.exception.DataLoadingException;
import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.RecoverableDataAccessException;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoField;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@DisplayName("Класс CommentServiceImpl должен ")
@SpringBootTest
class CommentServiceImplTest {

    private static final String COMMENT_NOT_FOUND = "Комментарий с id '%d' не найден";
    private static final String ERROR_INSERT = "При добавлении комментария '%s' произошла ошибка";
    private static final long EXPECTED_COMMENT_COUNT = 1L;
    private static final Long TEST_COMMENT_ID = 1L;
    private static final String TEST_COMMENT_TEXT = "test comment text";
    private static final String UPDATED_COMMENT_TEXT = "updated comment text";
    private static final String TEST_COMMENTATOR = "test commentator";
    private static final ZonedDateTime TEST_ZONED_DATE_TIME = ZonedDateTime.now().with(ChronoField.NANO_OF_SECOND, 0);

    private static final long TEST_AUTHOR_ID = 1L;
    private static final String TEST_AUTHOR_NAME = "test author name";
    private static final Author TEST_AUTHOR = new Author(TEST_AUTHOR_NAME);

    private static final long TEST_GENRE_ID = 1L;
    private static final String TEST_GENRE_NAME = "test genre name";
    private static final Genre TEST_GENRE = new Genre(TEST_GENRE_ID, TEST_GENRE_NAME);

    private static final long TEST_BOOK_ID = 1L;
    private static final String TEST_BOOK_TITLE = "test book title";
    private static final Book TEST_BOOK = new Book(TEST_BOOK_ID, TEST_BOOK_TITLE, TEST_AUTHOR, TEST_GENRE);

    private static final Comment TEST_COMMENT = new Comment(TEST_COMMENT_ID, TEST_COMMENT_TEXT, TEST_BOOK, TEST_ZONED_DATE_TIME, TEST_COMMENTATOR);
    private static final Comment UPDATED_TEST_COMMENT = new Comment(TEST_COMMENT_ID, UPDATED_COMMENT_TEXT, TEST_BOOK, TEST_ZONED_DATE_TIME, TEST_COMMENTATOR);

    @MockBean
    private BookService bookService;

    @MockBean
    private CommentDao dao;

    @Autowired
    private CommentServiceImpl service;

    @DisplayName("возвращать ожидаемое количество комментариев")
    @Test
    void shouldReturnExpectedCommentCount() {
        given(dao.count())
                .willReturn(EXPECTED_COMMENT_COUNT);
        assertThat(service.count()).isEqualTo(EXPECTED_COMMENT_COUNT);
    }

    @SneakyThrows
    @DisplayName("возвращать заданный комментарий по его id")
    @Test
    void shouldReturnExpectedCommentById() {
        given(dao.findById(TEST_COMMENT_ID))
                .willReturn(Optional.of(TEST_COMMENT));

        assertThat(service.getById(TEST_COMMENT_ID))
                .isEqualTo(TEST_COMMENT);
    }

    @DisplayName("кидать исключение, если нельзя получить заданный комментарий по его id")
    @Test
    void shouldThrowExceptionIfCantGetCommentById() {
        given(dao.findById(TEST_COMMENT_ID))
                .willReturn(Optional.empty());

        Throwable thrown = assertThrows(DataLoadingException.class, () -> {
            service.getById(TEST_COMMENT_ID);
        });
        assertThat(thrown.getMessage()).isEqualTo(String.format(COMMENT_NOT_FOUND, TEST_COMMENT_ID));
    }

    @SneakyThrows
    @DisplayName("добавлять комментарий")
    @Test
    void shouldAddComment() {
        given(bookService.getById(TEST_COMMENT.getBook().getId()))
                .willReturn(TEST_BOOK);

        Comment comment = TEST_COMMENT;
        comment.setId(null);
        comment.setDatetime(ZonedDateTime.now().with(ChronoField.NANO_OF_SECOND, 0));

        service.add(TEST_COMMENT_TEXT, TEST_BOOK_ID, TEST_COMMENTATOR);
        verify(dao, times(1)).save(comment);
    }

    @SneakyThrows
    @DisplayName("кидать исключение, если нельзя добавить комментарий")
    @Test
    void shouldThrowExceptionIfCantAddComment() {
        doThrow(RecoverableDataAccessException.class).when(dao).save(isA(Comment.class));
        given(bookService.getById(isA(Long.class)))
                .willReturn(TEST_BOOK);

        Throwable thrown = assertThrows(DataLoadingException.class, () -> {
            service.add(TEST_COMMENT_TEXT, TEST_BOOK_ID, TEST_COMMENTATOR);
        });
        assertThat(thrown.getMessage()).isEqualTo(String.format(ERROR_INSERT, TEST_COMMENT_TEXT));
    }

    @SneakyThrows
    @DisplayName("удалять комментарий по id")
    @Test
    void shouldDeleteCommentById() {
        given(dao.existsById(TEST_COMMENT_ID))
                .willReturn(true);

        service.deleteById(TEST_COMMENT_ID);
        verify(dao, times(1)).deleteById(TEST_COMMENT_ID);
    }

    @DisplayName("кидать исключение, если нельзя удалить комментарий")
    @Test
    void shouldThrowExceptionIfCantDeleteComment() {
        given(dao.existsById(TEST_COMMENT_ID))
                .willReturn(false);

        Throwable thrown = assertThrows(DataLoadingException.class, () -> {
            service.deleteById(TEST_COMMENT_ID);
        });
        assertThat(thrown.getMessage()).isEqualTo(String.format(COMMENT_NOT_FOUND, TEST_COMMENT_ID));
    }

    @DisplayName(" возвращать все комментарий")
    @Test
    void shouldGetAllComments() {
        List<Comment> expectedCommentList = Collections.singletonList(TEST_COMMENT);

        given(dao.findAll())
                .willReturn(expectedCommentList);

        List<Comment> actualCommentList = service.getAll();
        assertThat(actualCommentList).isEqualTo(expectedCommentList);
    }

    @SneakyThrows
    @DisplayName(" возвращать все комментарии к книге")
    @Test
    void shouldGetAllCommentsByBook() {
        List<Comment> expectedCommentList = Collections.singletonList(TEST_COMMENT);

        given(dao.findAllByBook(TEST_BOOK))
                .willReturn(expectedCommentList);

        given(bookService.getById(TEST_BOOK.getId()))
                .willReturn(TEST_BOOK);

        List<Comment> actualCommentList = service.getByBookId(TEST_BOOK_ID);
        assertThat(actualCommentList).isEqualTo(expectedCommentList);
    }

    @SneakyThrows
    @DisplayName(" обновлять комментарий")
    @Test
    void shouldUpdateComment() {
        given(dao.existsById(TEST_COMMENT_ID))
                .willReturn(true);

        given(dao.findById(UPDATED_TEST_COMMENT.getId()))
                .willReturn(Optional.of(UPDATED_TEST_COMMENT));

        service.updateById(TEST_COMMENT_ID, UPDATED_COMMENT_TEXT);
        verify(dao, times(1)).save(UPDATED_TEST_COMMENT);
    }

    @DisplayName("кидать исключение при обновлении комментария, если комментарий не найден")
    @Test
    void shouldThrowExceptionWhileUpdateIfCommentNotFound() {
        given(dao.existsById(TEST_COMMENT_ID))
                .willReturn(false);

        Throwable thrown = assertThrows(DataLoadingException.class, () -> {
            service.updateById(TEST_COMMENT_ID, TEST_COMMENT_TEXT);
        });
        assertThat(thrown.getMessage()).isEqualTo(String.format(COMMENT_NOT_FOUND, TEST_COMMENT_ID));
    }
}
