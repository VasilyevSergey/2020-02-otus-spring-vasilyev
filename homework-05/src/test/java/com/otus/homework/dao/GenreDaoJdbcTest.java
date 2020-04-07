package com.otus.homework.dao;

import com.otus.homework.domain.Genre;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Dao для работы с жанрами должен ")
@JdbcTest
@Import(GenreDaoJdbc.class)
class GenreDaoJdbcTest {

    private static final int EXPECTED_GENRES_COUNT = 2;
    private static final String INSERTED_GENRE_NAME = "NewGenre";
    private static final long INSERTED_GENRE_ID = 3L;
    private static final long EXPECTED_FIRST_GENRE_ID = 1L;
    private static final String EXPECTED_FIRST_GENRE_NAME = "A poem in verse";
    private static final long EXPECTED_SECOND_GENRE_ID = 2L;
    private static final String EXPECTED_SECOND_GENRE_NAME = "Fantasy";

    @Autowired
    private GenreDaoJdbc dao;

    @DisplayName("возвращать ожидаемое количество жанров")
    @Test
    void shouldReturnExpectedGenreCount() {

        int count = dao.count();
        assertThat(count).isEqualTo(EXPECTED_GENRES_COUNT);
    }

    @DisplayName("возвращать заданный жанр по его Id")
    @Test
    void shouldReturnExpectedAuthorById() {
        Genre expectedGenre = new Genre(EXPECTED_FIRST_GENRE_ID, EXPECTED_FIRST_GENRE_NAME);
        Genre actualGenre = dao.getById(EXPECTED_FIRST_GENRE_ID);
        assertThat(actualGenre).isEqualTo(expectedGenre);
    }

    @DisplayName(" добавлять жанр")
    @Test
    void shouldInsertGenre() {
        Genre expectedGenre = new Genre(INSERTED_GENRE_ID, INSERTED_GENRE_NAME);
        dao.insert(expectedGenre);
        Genre actualGenre = dao.getById(INSERTED_GENRE_ID);
        assertThat(actualGenre).isEqualTo(expectedGenre);
    }

    @DisplayName(" возвращать все жанры")
    @Test
    void shouldGetAllGenres() {
        List<Genre> expectedGenreList = Arrays.asList(
                new Genre(EXPECTED_FIRST_GENRE_ID, EXPECTED_FIRST_GENRE_NAME),
                new Genre(EXPECTED_SECOND_GENRE_ID, EXPECTED_SECOND_GENRE_NAME)
        );

        List<Genre> actualGenreList = dao.getAll();
        assertThat(actualGenreList).isEqualTo(expectedGenreList);
    }
}
