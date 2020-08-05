package com.otus.homework.controller;

import com.otus.homework.domain.Author;
import com.otus.homework.repository.AuthorRepository;
import com.otus.homework.repository.BookRepository;
import com.otus.homework.repository.CommentRepository;
import com.otus.homework.repository.GenreRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthorController.class)
@DisplayName("Controller для работы с авторами должен ")
class AuthorControllerTest {
    private static final Author EXPECTED_AUTHOR = new Author("1", "Pushkin");

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AuthorRepository authorRepository;

    @MockBean
    private CommentRepository commentRepository;

    @MockBean
    private GenreRepository genreRepository;

    @MockBean
    private BookRepository bookRepository;

    @Test
    void listPage() throws Exception {
        this.mvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("author/list"));
    }

    @Test
    void editPage() throws Exception {
        this.mvc.perform(get("/author/edit/" + EXPECTED_AUTHOR.getId()))
                .andExpect(status().isOk())
                .andExpect(view().name("author/edit"));
    }
}
