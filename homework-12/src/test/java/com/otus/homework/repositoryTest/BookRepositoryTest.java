package com.otus.homework.repositoryTest;

import com.otus.homework.domain.Book;
import com.otus.homework.domain.Comment;
import com.otus.homework.repository.BookRepository;
import com.otus.homework.repository.CommentRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;


@DataMongoTest
@EnableConfigurationProperties
@ComponentScan({"com.otus.homework.config", "com.otus.homework.events"})
@DisplayName("Dao для работы с книгами должен ")
class BookRepositoryTest {

    private static final String BOOK_TO_DELETE_ID = "1";

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CommentRepository commentRepository;

    @MockBean
    @Qualifier("mongoUserDetailsServiceImpl")
    private UserDetailsService userDetailsService;

    @DisplayName(" удалять книгу и комментарии к книге")
    @Test
    void shouldDeleteBookAndComments() {
        // загружаем книгу и комментарии к книге
        Optional<Book> book = bookRepository.findById(BOOK_TO_DELETE_ID);
        if (book.isEmpty()) {
            fail("Книга не найдена");
        }
        List<Comment> comments = commentRepository.findAllByBook(book.get());
        assertThat(comments.size()).isGreaterThan(0);

        // удаляем книгу
        bookRepository.deleteById(BOOK_TO_DELETE_ID);

        // проверяем, что книга и комментарии удалены
        book = bookRepository.findById(BOOK_TO_DELETE_ID);
        if (book.isPresent()) {
            fail("Не удалось удалить книгу");
        }
        List commentsOfDeletedBook = commentRepository.findAll()
                .stream()
                .filter(comment -> comment.getBook()
                        .getId()
                        .equals(BOOK_TO_DELETE_ID))
                .collect(Collectors.toList());
        assertThat(commentsOfDeletedBook.size()).isEqualTo(0);
    }
}
