package com.otus.homework.dao;

import com.otus.homework.domain.Author;
import com.otus.homework.domain.Book;
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
import static org.junit.Assert.assertNull;

@DisplayName("Dao для работы с книгами должен ")
@DataJpaTest
class BookDaoTest {

    private static final long EXPECTED_BOOKS_COUNT = 2L;

    private static final Book EXPECTED_FIRST_BOOK = new Book(
            1L,
            "Ruslan and Lyudmila",
            new Author("Pushkin"),
            new Genre(1L, "A poem in verse")
    );

    private static final Book EXPECTED_SECOND_BOOK = new Book(
            2L,
            "Lord of the Rings",
            new Author("Tolkien"),
            new Genre(2L, "Fantasy")
    );

    private static final Book EXPECTED_NEW_BOOK = new Book(
            null,
            "new title",
            new Author("Pushkin"),
            new Genre(1L, "A poem in verse")
    );

    private static final Book EXPECTED_UPDATE_BOOK = new Book(
            1L,
            "Updated title",
            new Author(1L, "Pushkin"),
            new Genre(1L, "A poem in verse")
    );

    @Autowired
    private TestEntityManager em;

    @Autowired
    private BookDao dao;

    @DisplayName("возвращать ожидаемое количество авторов")
    @Test
    void shouldReturnExpectedBookCount() {
        long count = dao.count();
        assertThat(count).isEqualTo(EXPECTED_BOOKS_COUNT);
    }

    @DisplayName("возвращать заданную книгу по ее id")
    @Test
    void shouldReturnExpectedBookById() {
        val optionalActualBook = dao.findById(EXPECTED_FIRST_BOOK.getId());
        val expectedBook = em.find(Book.class, EXPECTED_FIRST_BOOK.getId());
        assertThat(optionalActualBook).isPresent().get()
                .isEqualToComparingFieldByField(expectedBook);
    }

    @DisplayName(" добавлять книгу")
    @Test
    void shouldInsertBook() {
        dao.save(EXPECTED_NEW_BOOK);
        Book actualBook = em.find(Book.class, EXPECTED_NEW_BOOK.getId());
        assertThat(actualBook).isEqualTo(EXPECTED_NEW_BOOK);
    }

    @DisplayName(" удалять книгу")
    @Test
    void shouldDeleteBook() {
        dao.deleteById(EXPECTED_FIRST_BOOK.getId());
        Book book = em.find(Book.class, EXPECTED_FIRST_BOOK.getId());
        assertNull(book);
    }

    @DisplayName(" возвращать все книги")
    @Test
    void shouldGetAllBooks() {
        val actualBookList = dao.findAll();
        List<Book> expectedBookList = Arrays.asList(
                em.find(Book.class, EXPECTED_FIRST_BOOK.getId()),
                em.find(Book.class, EXPECTED_SECOND_BOOK.getId())
        );
        assertThat(actualBookList).isEqualTo(expectedBookList);
    }

    @DisplayName(" обновлять книгу")
    @Test
    void shouldUpdateBook() {
        dao.save(EXPECTED_UPDATE_BOOK);
        Book actualUpdatedBook = em.find(Book.class, EXPECTED_UPDATE_BOOK.getId());
        assertThat(actualUpdatedBook).isEqualTo(EXPECTED_UPDATE_BOOK);
    }
}
