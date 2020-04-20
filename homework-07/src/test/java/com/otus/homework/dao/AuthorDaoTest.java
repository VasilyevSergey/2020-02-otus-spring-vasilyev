package com.otus.homework.dao;

import com.otus.homework.domain.Author;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNull;

@DisplayName("Dao для работы с авторами должен ")
@DataJpaTest
class AuthorDaoTest {

    private static final long EXPECTED_AUTHORS_COUNT = 2;
    private static final String INSERTED_AUTHOR_NAME = "NewAuthor";
    private static final long INSERTED_AUTHOR_ID = 3L;
    private static final long DELETE_AUTHOR_ID = 2L;
    private static final long EXPECTED_FIRST_AUTHOR_ID = 1L;
    private static final long EXPECTED_SECOND_AUTHOR_ID = 2L;

    @Autowired
    private TestEntityManager em;

    @Autowired
    private AuthorDao dao;

    @DisplayName("возвращать ожидаемое количество авторов")
    @Test
    void shouldReturnExpectedAuthorCount() {
        long count = dao.count();
        assertThat(count).isEqualTo(EXPECTED_AUTHORS_COUNT);
    }

    @DisplayName("возвращать заданного автора по его id")
    @Test
    void shouldReturnExpectedAuthorById() {
        val optionalActualAuthor = dao.findById(EXPECTED_FIRST_AUTHOR_ID);
        val expectedAuthor = em.find(Author.class, EXPECTED_FIRST_AUTHOR_ID);
        assertThat(optionalActualAuthor).isPresent().get()
                .isEqualToComparingFieldByField(expectedAuthor);
    }

    @DisplayName(" добавлять автора")
    @Test
    void shouldInsertAuthor() {
        Author expectedAuthor = new Author(INSERTED_AUTHOR_NAME);
        dao.save(expectedAuthor);
        Author actualAuthor = em.find(Author.class, INSERTED_AUTHOR_ID);
        assertThat(actualAuthor).isEqualTo(expectedAuthor);
    }

    @DisplayName(" удалять автора")
    @Test
    void shouldDeleteAuthor() {
        dao.deleteById(DELETE_AUTHOR_ID);
        Author author = em.find(Author.class, DELETE_AUTHOR_ID);
        assertNull(author);
    }

    @DisplayName(" возвращать всех авторов")
    @Test
    void shouldGetAllAuthors() {
        val actualAuthorList = dao.findAll();
        List<Author> expectedAuthorList = Arrays.asList(
                em.find(Author.class, EXPECTED_FIRST_AUTHOR_ID),
                em.find(Author.class, EXPECTED_SECOND_AUTHOR_ID)
        );
        assertThat(actualAuthorList).isEqualTo(expectedAuthorList);
    }
}
