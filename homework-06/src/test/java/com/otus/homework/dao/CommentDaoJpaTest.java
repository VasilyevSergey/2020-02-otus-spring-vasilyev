package com.otus.homework.dao;

import com.otus.homework.domain.Author;
import com.otus.homework.domain.Book;
import com.otus.homework.domain.Comment;
import com.otus.homework.domain.Genre;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoField;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNull;

@DisplayName("Dao для работы с комментариями должен ")
@DataJpaTest
@Import(CommentDaoJpa.class)
class CommentDaoJpaTest {
    private static final int EXPECTED_COMMENTS_COUNT = 3;
    private static final long EXPECTED_FIRST_COMMENT_ID = 1L;
    private static final long EXPECTED_SECOND_COMMENT_ID = 2L;
    private static final long EXPECTED_THIRD_COMMENT_ID = 3L;

    private static final long DELETE_COMMENT_ID = 1L;
    private static final Book BOOK = new Book(
            1L,
            "Ruslan and Lyudmila",
            new Author(1L, "Pushkin"),
            new Genre(1L, "A poem in verse")
    );

    private static final Comment ADDED_COMMENT = new Comment(
            4L,
            "added comment text",
            BOOK,
            ZonedDateTime.now(),
            "Vasya"
    );

    private static final Comment EXPECTED_UPDATED_COMMENT = new Comment(
            1L,
            "updated comment text",
            BOOK,
            ZonedDateTime.now().with(ChronoField.NANO_OF_SECOND, 0),
            "Vasya"
    );


    @Autowired
    private TestEntityManager em;

    @Autowired
    private CommentDaoJpa dao;

    @DisplayName("возвращать ожидаемое количество комментариев")
    @Test
    void shouldReturnExpectedCommentCount() {

        int count = dao.count();
        assertThat(count).isEqualTo(EXPECTED_COMMENTS_COUNT);
    }

    @DisplayName("возвращать заданный комментарий по его id")
    @Test
    void shouldReturnExpectedCommentById() {
        val optionalActualComment = dao.getById(EXPECTED_FIRST_COMMENT_ID);
        val expectedComment = em.find(Comment.class, EXPECTED_FIRST_COMMENT_ID);
        assertThat(optionalActualComment).isPresent().get()
                .isEqualToComparingFieldByField(expectedComment);
    }

    @DisplayName(" добавлять комментарий")

    @Test
    void shouldAddComment() {
        dao.add(ADDED_COMMENT);
        Comment actualComment = em.find(Comment.class, ADDED_COMMENT.getId());
        assertThat(actualComment).isEqualTo(ADDED_COMMENT);
    }

    @DisplayName(" удалять комментарий")
    @Test
    void shouldDeleteComment() {
        dao.deleteById(DELETE_COMMENT_ID);
        Comment comment = em.find(Comment.class, DELETE_COMMENT_ID);
        assertNull(comment);
    }

    @DisplayName(" возвращать все комментарии")
    @Test
    void shouldGetAllComments() {
        val actualCommentList = dao.getAll();
        List<Comment> expectedCommentList = Arrays.asList(
                em.find(Comment.class, EXPECTED_FIRST_COMMENT_ID),
                em.find(Comment.class, EXPECTED_SECOND_COMMENT_ID),
                em.find(Comment.class, EXPECTED_THIRD_COMMENT_ID)
        );
        assertThat(actualCommentList).isEqualTo(expectedCommentList);
    }

    @DisplayName(" возвращать все комментарии к книге")
    @Test
    void shouldGetCommentsByBook() {
        val actualCommentList = dao.getByBook(BOOK);
        List<Comment> expectedCommentList = Arrays.asList(
                em.find(Comment.class, EXPECTED_FIRST_COMMENT_ID),
                em.find(Comment.class, EXPECTED_SECOND_COMMENT_ID)
        );
        assertThat(actualCommentList).isEqualTo(expectedCommentList);
    }

    @DisplayName(" обновлять комментарий")
    @Test
    void shouldUpdateComment() {
        dao.update(EXPECTED_UPDATED_COMMENT);
        Comment actualUpdatedComment = em.find(Comment.class, EXPECTED_UPDATED_COMMENT.getId());
        assertThat(actualUpdatedComment).isEqualTo(EXPECTED_UPDATED_COMMENT);
    }
}
