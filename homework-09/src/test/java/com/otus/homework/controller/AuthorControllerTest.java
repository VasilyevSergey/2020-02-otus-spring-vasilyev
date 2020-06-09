package com.otus.homework.controller;

import com.otus.homework.domain.Author;
import com.otus.homework.repository.AuthorRepository;
import com.otus.homework.repository.BookRepository;
import com.otus.homework.repository.CommentRepository;
import com.otus.homework.repository.GenreRepository;
import com.otus.homework.service.AuthorService;
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

@WebMvcTest(AuthorController.class)
@DisplayName("Controller для работы с авторами должен ")
class AuthorControllerTest {
    private static final Author EXPECTED_AUTHOR = new Author("1", "Pushkin");
    private static final Author UPDATED_AUTHOR = new Author("1", "Updated author name");
    private static final Author NEW_AUTHOR = new Author( "3", "New author name");

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
    void authorList() throws Exception {
        List<Author> expectedAuthorList = Collections.singletonList(EXPECTED_AUTHOR);

        given(authorService.getAll())
                .willReturn(expectedAuthorList);

        this.mvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content().string(Matchers.containsString(EXPECTED_AUTHOR.getName())));
    }

    @Test
    void editAuthor() throws Exception {
        given(authorService.getById(EXPECTED_AUTHOR.getId()))
                .willReturn(EXPECTED_AUTHOR);

        this.mvc.perform(get("/author/edit/" + EXPECTED_AUTHOR.getId()))
                .andExpect(status().isOk())
                .andExpect(content().string(Matchers.containsString(EXPECTED_AUTHOR.getName())));

    }

    @Test
    void editAuthorPost() throws Exception {
        given(authorService.updateById(UPDATED_AUTHOR))
                .willReturn(UPDATED_AUTHOR);

        MockHttpServletRequestBuilder editAuthor = post("/author/edit")
                .param("id", UPDATED_AUTHOR.getId())
                .param("name", UPDATED_AUTHOR.getName());

        mvc.perform(editAuthor)
                .andExpect(status().isOk())
                .andExpect(content().string(Matchers.containsString(UPDATED_AUTHOR.getName())));
    }

    @Test
    void addAuthor() throws Exception {
        given(authorService.insert(NEW_AUTHOR.getName()))
                .willReturn(NEW_AUTHOR);

        List<Author> expectedAuthorList = Arrays.asList(EXPECTED_AUTHOR, NEW_AUTHOR);
        given(authorService.getAll())
                .willReturn(expectedAuthorList);

        MockHttpServletRequestBuilder addAuthor = post("/author/add")
                .param("newAuthorName", NEW_AUTHOR.getName());

        mvc.perform(addAuthor)
                .andExpect(status().isOk())
                .andExpect(content().string(Matchers.containsString(EXPECTED_AUTHOR.getName())))
                .andExpect(content().string(Matchers.containsString(NEW_AUTHOR.getName())));
    }

    @Test
    void deleteAuthor() throws Exception {
        List<Author> expectedAuthorList = Collections.singletonList(EXPECTED_AUTHOR);
        given(authorService.getAll())
                .willReturn(expectedAuthorList);

        MockHttpServletRequestBuilder deleteAuthor = post("/author/delete/2");

        mvc.perform(deleteAuthor)
                .andExpect(status().isOk())
                .andExpect(content().string(Matchers.containsString(EXPECTED_AUTHOR.getName())));
    }
}
