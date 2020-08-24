package com.otus.homework.repositoryTest;

import com.otus.homework.domain.Author;
import com.otus.homework.domain.Book;
import com.otus.homework.domain.Meeting;
import com.otus.homework.exception.DataLoadingException;
import com.otus.homework.repository.BookRepository;
import com.otus.homework.repository.MeetingRepository;
import com.otus.homework.service.MongoUserDetailsServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.apache.tools.ant.taskdefs.SetPermissions.NonPosixMode.fail;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.fail;

@DataMongoTest
@EnableConfigurationProperties
@ComponentScan({"com.otus.homework.config", "com.otus.homework.events"})
@DisplayName("Dao для работы с книгами ")
class BookRepositoryTest {
    private static final Author AUTHOR = new Author("1", "Pushkin");

    private static final Book EXPECTED_BOOK = new Book(
            "1",
            "Ruslan and Lyudmila",
            AUTHOR);

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private MeetingRepository meetingRepository;

    @MockBean
    private MongoUserDetailsServiceImpl userDetailsService;

    @DisplayName("возвращать все книги автора")
    @Test
    void shouldFindAllBooksByAuthor() {
        List<Book> actualBookList = bookRepository.findAllByAuthor(AUTHOR);
        List<Book> expectedBookList = Collections.singletonList(EXPECTED_BOOK);
        assertThat(actualBookList).isEqualTo(expectedBookList);
    }

    @DisplayName("удалять книгу и чистить списки книг во встречах")
    @Test
    void shouldDeleteBookAndClearBookLists() {
        // загружаем книгу и встречи по этой книге
        List<Meeting> meetingList = meetingRepository.findAllByBookListContaining(EXPECTED_BOOK);
        assertThat(meetingList.size()).isGreaterThan(0);

        // удаляем книгу
        bookRepository.deleteById(EXPECTED_BOOK.getId());

        // проверяем, что книга удалена и убрана из списка книг во всех встречах
        Optional<Book> book = bookRepository.findById(EXPECTED_BOOK.getId());
        if (book.isPresent()) {
            fail("Не удалось удалить книгу");
        }
        meetingList = meetingRepository.findAllByBookListContaining(EXPECTED_BOOK);
        assertThat(meetingList.size()).isEqualTo(0);
    }
}
