package com.otus.homework.dao;

import com.otus.homework.domain.Genre;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Dao для работы с жанрами должен ")
@DataJpaTest
class GenreRepositoryTest {

    private static final int EXPECTED_GENRES_COUNT = 2;
    private static final String INSERTED_GENRE_NAME = "NewGenre";
    private static final long INSERTED_GENRE_ID = 3L;
    private static final long EXPECTED_FIRST_GENRE_ID = 1L;
    private static final long EXPECTED_SECOND_GENRE_ID = 2L;

    @Autowired
    private TestEntityManager em;

    @Autowired
    private GenreRepository dao;

    @DisplayName("возвращать ожидаемое количество жанров")
    @Test
    void shouldReturnExpectedGenreCount() {

        long count = dao.count();
        assertThat(count).isEqualTo(EXPECTED_GENRES_COUNT);
    }

    @DisplayName("возвращать заданный жанр по его id")
    @Test
    void shouldReturnExpectedAuthorById() {
        val optionalActualGenre = dao.findById(EXPECTED_FIRST_GENRE_ID);
        val expectedGenre = em.find(Genre.class, EXPECTED_FIRST_GENRE_ID);
        assertThat(optionalActualGenre).isPresent().get()
                .isEqualToComparingFieldByField(expectedGenre);
    }

    @DisplayName(" добавлять жанр")
    @Test
    void shouldInsertGenre() {
        Genre expectedGenre = new Genre(INSERTED_GENRE_ID, INSERTED_GENRE_NAME);
        dao.save(expectedGenre);
        Genre actualGenre = em.find(Genre.class, INSERTED_GENRE_ID);
        assertThat(actualGenre).isEqualTo(expectedGenre);
    }

    @DisplayName(" возвращать все жанры")
    @Test
    void shouldGetAllGenres() {
        val actualGenreList = dao.findAll();
        List<Genre> expectedGenreList = Arrays.asList(
                em.find(Genre.class, EXPECTED_FIRST_GENRE_ID),
                em.find(Genre.class, EXPECTED_SECOND_GENRE_ID)
        );
        assertThat(actualGenreList).isEqualTo(expectedGenreList);
    }
}
