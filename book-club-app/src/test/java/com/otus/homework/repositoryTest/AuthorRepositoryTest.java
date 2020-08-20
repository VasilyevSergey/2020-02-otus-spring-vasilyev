package com.otus.homework.repositoryTest;

import com.otus.homework.domain.Author;
import com.otus.homework.domain.Book;
import com.otus.homework.domain.Meeting;
import com.otus.homework.repository.AuthorRepository;
import com.otus.homework.repository.BookRepository;
import com.otus.homework.repository.MeetingRepository;
import com.otus.homework.service.MongoUserDetailsServiceImpl;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

@DataMongoTest
@EnableConfigurationProperties
@ComponentScan({"com.otus.homework.config", "com.otus.homework.events"})
@DisplayName("Dao для работы с авторами должен ")
class AuthorRepositoryTest {
    private static final Author FIRST_AUTHOR = new Author("1", "Pushkin");
    private static final Author SECOND_AUTHOR = new Author("2", "Tolkien");
    private static final Author INSERTED_AUTHOR = new Author("3", "NewAuthor");

    private static final Book EXPECTED_BOOK = new Book(
            "1",
            "Ruslan and Lyudmila",
            FIRST_AUTHOR);

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private MeetingRepository meetingRepository;

    @MockBean
    private MongoUserDetailsServiceImpl userDetailsService;

    @DisplayName("возвращать заданного автора по его id")
    @Test
    void shouldReturnExpectedAuthorById() {
        val optionalActualAuthor = authorRepository.findById(FIRST_AUTHOR.getId());
        assertThat(optionalActualAuthor).isPresent().get()
                .isEqualToComparingFieldByField(FIRST_AUTHOR);
    }

    @DisplayName(" добавлять автора")
    @Test
    void shouldInsertAuthor() {
        authorRepository.save(INSERTED_AUTHOR);
        Author actualAuthor = authorRepository.findById(INSERTED_AUTHOR.getId()).get();
        assertThat(actualAuthor).isEqualTo(INSERTED_AUTHOR);
    }

    @DisplayName(" возвращать всех авторов")
    @Test
    void shouldGetAllAuthors() {
        val actualAuthorList = authorRepository.findAll();
        List<Author> expectedAuthorList = Arrays.asList(FIRST_AUTHOR, SECOND_AUTHOR);
        assertThat(actualAuthorList).isEqualTo(expectedAuthorList);
    }

    @DisplayName("удалять автора, его книги и чистить списки книг во встречах")
    @Test
    // если поменять название теста, например, на shouldDeleteAuthorAndBooks,
    // то падают тесты shouldGetAllAuthors() и shouldReturnExpectedAuthorById()
    void deleteAuthorTest() {
        Optional<Author> author = authorRepository.findById(FIRST_AUTHOR.getId());
        if (author.isEmpty()) {
            fail("Автора не существует");
        }

        // проверяем, что книги автора и встречи по этим книгам существуют
        List<Book> bookList = bookRepository.findAllByAuthor(author.get());
        assertThat(bookList.size()).isGreaterThan(0);

        Book book = bookList.get(0);
        List<Meeting> meetingList = meetingRepository.findAllByBookListContaining(book);
        assertThat(meetingList.size()).isGreaterThan(0);

        authorRepository.deleteById(FIRST_AUTHOR.getId());
        assertThat(1).isEqualTo(1);

        // проверяем, что удален автор, его книги и списки книг во встречах очищены от его книг
        Optional<Author> deletedAuthor = authorRepository.findById(FIRST_AUTHOR.getId());
        if (deletedAuthor.isPresent()) {
            fail("Не удалось удалить автора");
        }

        bookList = bookRepository.findAllByAuthor(FIRST_AUTHOR);
        assertThat(bookList.size()).isEqualTo(0);

        meetingList = meetingRepository.findAllByBookListContaining(book);
        assertThat(meetingList.size()).isEqualTo(0);
    }
}
