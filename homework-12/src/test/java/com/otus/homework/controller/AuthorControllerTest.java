package com.otus.homework.controller;

import com.otus.homework.domain.Author;
import com.otus.homework.repository.*;
import com.otus.homework.service.AuthorService;
import com.otus.homework.service.MongoUserDetailsServiceImpl;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.Collections;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthorController.class)
@DisplayName("Controller для работы с авторами должен ")
class AuthorControllerTest {
    private static final Author EXPECTED_AUTHOR = new Author("1", "Pushkin");
    private static final Author UPDATED_AUTHOR = new Author("1", "Updated author name");
    private static final Author NEW_AUTHOR = new Author("3", "New author name");

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

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private MongoUserDetailsServiceImpl userDetailsService;

    @Test
    void givenUnauthenticatedAuthorList() throws Exception {
        this.mvc.perform(get("/"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    @WithMockUser(username = "user", authorities = {"user"})
    @Test
    void authorList() throws Exception {
        List<Author> expectedAuthorList = Collections.singletonList(EXPECTED_AUTHOR);

        given(authorService.getAll())
                .willReturn(expectedAuthorList);

        this.mvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content().string(Matchers.containsString(EXPECTED_AUTHOR.getName())));
    }

    @WithMockUser(username = "user", authorities = {"user"})
    @Test
    void editAuthor() throws Exception {
        given(authorService.getById(EXPECTED_AUTHOR.getId()))
                .willReturn(EXPECTED_AUTHOR);

        this.mvc.perform(get("/author/edit/" + EXPECTED_AUTHOR.getId()))
                .andExpect(status().isOk())
                .andExpect(content().string(Matchers.containsString(EXPECTED_AUTHOR.getName())));

    }

    @WithMockUser(username = "user", authorities = {"user"})
    @Test
    void editAuthorPost() throws Exception {
        MockHttpServletRequestBuilder editAuthor = post("/author/edit")
                .param("id", UPDATED_AUTHOR.getId())
                .param("name", UPDATED_AUTHOR.getName());

        mvc.perform(editAuthor)
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));

        verify(authorService, times(1)).updateById(UPDATED_AUTHOR);
    }

    @WithMockUser(username = "user", authorities = {"user"})
    @Test
    void addAuthor() throws Exception {
        MockHttpServletRequestBuilder addAuthor = post("/author/add")
                .param("newAuthorName", NEW_AUTHOR.getName());

        mvc.perform(addAuthor)
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));

        verify(authorService, times(1)).insert(NEW_AUTHOR.getName());
    }

    @WithMockUser(username = "user", authorities = {"user"})
    @Test
    void deleteAuthor() throws Exception {
        MockHttpServletRequestBuilder deleteAuthor = post("/author/delete/" + EXPECTED_AUTHOR.getId());

        mvc.perform(deleteAuthor)
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));

        verify(authorService, times(1)).deleteById(EXPECTED_AUTHOR.getId());
    }
}
