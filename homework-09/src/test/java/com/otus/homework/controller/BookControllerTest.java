package com.otus.homework.controller;

import com.otus.homework.domain.Author;
import com.otus.homework.domain.Book;
import com.otus.homework.domain.Genre;
import com.otus.homework.repository.AuthorRepository;
import com.otus.homework.repository.BookRepository;
import com.otus.homework.repository.CommentRepository;
import com.otus.homework.repository.GenreRepository;
import com.otus.homework.service.AuthorService;
import com.otus.homework.service.BookService;
import com.otus.homework.service.GenreService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
@DisplayName("Controller для работы с книгами должен ")
class BookControllerTest {
    private static final Author TEST_AUTHOR = new Author("1", "test author name");
    private static final Genre TEST_GENRE = new Genre("1", "test genre name");
    private static final Book TEST_BOOK = new Book("1", "test book title", TEST_AUTHOR, TEST_GENRE);

    private static final Author UPDATED_AUTHOR = new Author("1", "Updated author name");
    private static final Genre UPDATED_GENRE = new Genre("1", "Updated genre name");
    private static final Book UPDATED_BOOK = new Book("1", "Updated book title", UPDATED_AUTHOR, UPDATED_GENRE);

    private static final Book BOOK_TO_DELETE = new Book("2", "book to delete title", TEST_AUTHOR, TEST_GENRE);
    private static final Book BOOK_TO_ADD = new Book("2", "book to add title", TEST_AUTHOR, TEST_GENRE);

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AuthorService authorService;

    @MockBean
    private BookService bookService;

    @MockBean
    private GenreService genreService;

    @MockBean
    private AuthorRepository authorRepository;

    @MockBean
    private CommentRepository commentRepository;

    @MockBean
    private GenreRepository genreRepository;

    @MockBean
    private BookRepository bookRepository;

    @Test
    void bookList() throws Exception {

        List<Book> expectedBookList = Collections.singletonList(TEST_BOOK);
        given(bookService.getByAuthorId(TEST_AUTHOR.getId()))
                .willReturn(expectedBookList);

        List<Genre> expectedGenreList = Collections.singletonList(TEST_GENRE);
        given(genreService.getAll())
                .willReturn(expectedGenreList);

        given(authorService.getById(TEST_AUTHOR.getId()))
                .willReturn(TEST_AUTHOR);

        this.mvc.perform(get("/book/list-by-author/" + TEST_AUTHOR.getId()))
                .andExpect(status().isOk())
                .andExpect(content().string(Matchers.containsString(TEST_BOOK.getTitle())))
                .andExpect(content().string(Matchers.containsString(TEST_BOOK.getAuthor().getName())))
                .andExpect(content().string(Matchers.containsString(TEST_BOOK.getGenre().getName())));
    }

    @Test
    void editBook() throws Exception {
        given(bookService.getById(TEST_BOOK.getId()))
                .willReturn(TEST_BOOK);

        List<Author> expectedAuthorList = Collections.singletonList(TEST_AUTHOR);
        given(authorService.getAll())
                .willReturn(expectedAuthorList);

        List<Genre> expectedGenreList = Collections.singletonList(TEST_GENRE);
        given(genreService.getAll())
                .willReturn(expectedGenreList);

        this.mvc.perform(get("/book/edit/" + TEST_BOOK.getId()))
                .andExpect(status().isOk())
                .andExpect(content().string(Matchers.containsString(TEST_BOOK.getId())))
                .andExpect(content().string(Matchers.containsString(TEST_BOOK.getTitle())))
                .andExpect(content().string(Matchers.containsString(TEST_BOOK.getAuthor().getName())))
                .andExpect(content().string(Matchers.containsString(TEST_BOOK.getGenre().getName())));
    }

    @Test
    void editBookPost() throws Exception {
        given(authorService.getById(UPDATED_AUTHOR.getId()))
                .willReturn(UPDATED_AUTHOR);

        given(genreService.getById(UPDATED_GENRE.getId()))
                .willReturn(UPDATED_GENRE);

        given(bookService.updateById(UPDATED_BOOK))
                .willReturn(UPDATED_BOOK);

        List<Author> expectedAuthorList = Collections.singletonList(UPDATED_AUTHOR);
        given(authorService.getAll())
                .willReturn(expectedAuthorList);

        List<Genre> expectedGenreList = Collections.singletonList(UPDATED_GENRE);
        given(genreService.getAll())
                .willReturn(expectedGenreList);

        MockHttpServletRequestBuilder editAuthor = post("/book/edit")
                .param("id", UPDATED_BOOK.getId())
                .param("title", UPDATED_BOOK.getTitle())
                .param("authorId", UPDATED_BOOK.getAuthor().getId())
                .param("genreId", UPDATED_BOOK.getGenre().getId());

        mvc.perform(editAuthor)
                .andExpect(status().isOk())
                .andExpect(content().string(Matchers.containsString(UPDATED_AUTHOR.getName())));
    }

    @Test
    void deleteBook() throws Exception {
        given(bookService.getById(BOOK_TO_DELETE.getId()))
                .willReturn(BOOK_TO_DELETE);

        List<Book> expectedBookList = Collections.singletonList(TEST_BOOK);
        given(bookService.getByAuthorId(TEST_AUTHOR.getId()))
                .willReturn(expectedBookList);

        List<Genre> expectedGenreList = Collections.singletonList(TEST_GENRE);
        given(genreService.getAll())
                .willReturn(expectedGenreList);

        MockHttpServletRequestBuilder deleteAuthor = post("/book/delete/" + BOOK_TO_DELETE.getId());

        mvc.perform(deleteAuthor)
                .andExpect(status().isOk())
                .andExpect(content().string(Matchers.containsString(TEST_AUTHOR.getName())));
    }

    @Test
    void addBook() throws Exception {
        given(bookService.insert(BOOK_TO_ADD.getTitle(),
                BOOK_TO_ADD.getAuthor().getId(),
                BOOK_TO_ADD.getGenre().getId()))
                .willReturn(BOOK_TO_ADD);

        List<Book> expectedBookList = Arrays.asList(TEST_BOOK, BOOK_TO_ADD);
        given(bookService.getByAuthorId(TEST_AUTHOR.getId()))
                .willReturn(expectedBookList);

        List<Genre> expectedGenreList = Collections.singletonList(TEST_GENRE);
        given(genreService.getAll())
                .willReturn(expectedGenreList);

        given(authorService.getById(TEST_AUTHOR.getId()))
                .willReturn(TEST_AUTHOR);


        MockHttpServletRequestBuilder addBook = post("/book/add")
                .param("title", BOOK_TO_ADD.getTitle())
                .param("genreId", BOOK_TO_ADD.getGenre().getId())
                .param("authorId", BOOK_TO_ADD.getAuthor().getId());

        mvc.perform(addBook)
                .andExpect(status().isOk())
                .andExpect(content().string(Matchers.containsString(TEST_BOOK.getTitle())))
                .andExpect(content().string(Matchers.containsString(BOOK_TO_ADD.getTitle())));
    }
}
