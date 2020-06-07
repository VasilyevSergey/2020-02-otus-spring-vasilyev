package com.otus.homework.controller;

import com.otus.homework.domain.Author;
import com.otus.homework.domain.Book;
import com.otus.homework.domain.Genre;
import com.otus.homework.repository.AuthorRepository;
import com.otus.homework.repository.BookRepository;
import com.otus.homework.repository.CommentRepository;
import com.otus.homework.repository.GenreRepository;
import com.otus.homework.service.AuthorService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthorController.class)
@DisplayName("Controller для работы с авторами должен ")
class AuthorControllerTest {
    private static final String EXPECTED_FIRST_AUTHOR_ID = "1";
    private static final Author EXPECTED_AUTHOR = new Author("1", "Pushkin");
    private static final Author FIRST_AUTHOR = new Author("1", "Pushkin");
    private static final Author SECOND_AUTHOR = new Author("2", "Tolkien");
    private static final Author INSERTED_AUTHOR = new Author("3", "NewAuthor");
    private static final Genre EXPECTED_GENRE = new Genre("1", "A poem in verse");

    private static final String TEST_AUTHOR_NAME = "test author name";
    private static final Author TEST_AUTHOR = new Author(TEST_AUTHOR_NAME);

    private static final Book EXPECTED_BOOK = new Book(
            "1",
            "Ruslan and Lyudmila",
            EXPECTED_AUTHOR,
            EXPECTED_GENRE);


    @Autowired
    private MockMvc mvc;

    @MockBean
    private AuthorService authorService;

    @MockBean
    private AuthorRepository authorRepository;

    @MockBean
    private CommentRepository commentRepository;

    @MockBean
    private GenreRepository genreRepository;

    @MockBean
    private BookRepository bookRepository;

    @Test
    void test() throws Exception {
        List<Author> expectedAuthorList = Collections.singletonList(TEST_AUTHOR);

        given(authorService.getAll())
                .willReturn(expectedAuthorList);

        given(authorService.insert(TEST_AUTHOR.getName()))
                .willReturn(TEST_AUTHOR);

        this.mvc.perform(get("/"))
                .andExpect(status().isOk());
                //.andExpect(content().string(""));

//        List<Author> actualAuthorList = service.getAll();
//        assertThat(actualAuthorList).isEqualTo(expectedAuthorList);
    }

    /*
    @DisplayName("возвращать заданного автора по его id")
    @Test
    void shouldReturnExpectedAuthorById() {
        val optionalActualAuthor = authorRepository.findById(EXPECTED_FIRST_AUTHOR_ID);
        assertThat(optionalActualAuthor).isPresent().get()
                .isEqualToComparingFieldByField(EXPECTED_AUTHOR);
    }

    @DisplayName(" добавлять автора")
    @Test
    void shouldInsertAuthor() {
        authorRepository.save(INSERTED_AUTHOR);
        Author actualAuthor = authorRepository.findById(INSERTED_AUTHOR.getId()).get();
        assertThat(actualAuthor).isEqualTo(INSERTED_AUTHOR);
    }

    @DisplayName(" удалять автора, его книги и комментарии к книгам")
    @Test
    void shouldDeleteAuthor() {
        // проверяем, что автор, его книга и комментарии существуют
        Optional<Author> author = authorRepository.findById(EXPECTED_AUTHOR.getId());
        if (author.isEmpty()) {
            fail("Автора не существует");
        }

        List<Book> books = bookRepository.findAllByAuthor(author.get());

        assertThat(books.size()).isEqualTo(1);
        assertThat(books.get(0)).isEqualTo(EXPECTED_BOOK);

        List<Comment> comments = commentRepository.findAllByBook(EXPECTED_BOOK);
        assertThat(comments.size()).isEqualTo(2);

        // удаляем автора
        authorRepository.deleteById(EXPECTED_AUTHOR.getId());

        // проверяем, что что автор, его книга и комментарии удалены
        author = authorRepository.findById(EXPECTED_AUTHOR.getId());
        if (author.isPresent()) {
            fail("Автор не удалён");
        }

        books = bookRepository.findAllByAuthor(EXPECTED_AUTHOR);
        assertThat(books.size()).isEqualTo(0);

        comments = commentRepository.findAllByBook(EXPECTED_BOOK);
        assertThat(comments.size()).isEqualTo(0);
    }

    @DisplayName(" возвращать всех авторов")
    @Test
    void shouldGetAllAuthors() {
        val actualAuthorList = authorRepository.findAll();
        List<Author> expectedAuthorList = Arrays.asList(FIRST_AUTHOR, SECOND_AUTHOR);
        assertThat(actualAuthorList).isEqualTo(expectedAuthorList);
    }

    */
}
