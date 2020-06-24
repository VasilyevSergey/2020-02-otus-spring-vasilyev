package com.otus.homework.restController;

import com.otus.homework.domain.Author;
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
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthorRestController.class)
@DisplayName("RestController для работы с авторами должен ")
class AuthorRestControllerTest {
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

    @Test
    void getAllAuthors() throws Exception {
        List<Author> expectedAuthorList = Collections.singletonList(EXPECTED_AUTHOR);

        given(authorService.getAll())
                .willReturn(expectedAuthorList);

        this.mvc.perform(get("/authors/"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(EXPECTED_AUTHOR.getId())))
                .andExpect(content().string(containsString(EXPECTED_AUTHOR.getName())));
    }

    @Test
    void editAuthor() throws Exception {
        MockHttpServletRequestBuilder editAuthor = put("/authors/" + UPDATED_AUTHOR.getId())
                .param("name", UPDATED_AUTHOR.getName());

        mvc.perform(editAuthor)
                .andExpect(status().isOk());

        verify(authorService, times(1)).updateById(UPDATED_AUTHOR);
    }

    @Test
    void addAuthor() throws Exception {
        MockHttpServletRequestBuilder addAuthor = post("/authors/")
                .param("newAuthorName", NEW_AUTHOR.getName());

        mvc.perform(addAuthor)
                .andExpect(status().isOk());

        verify(authorService, times(1)).insert(NEW_AUTHOR.getName());
    }

    @Test
    void deleteAuthor() throws Exception {
        MockHttpServletRequestBuilder deleteAuthor = delete("/authors/" + EXPECTED_AUTHOR.getId());

        mvc.perform(deleteAuthor)
                .andExpect(status().isOk());

        verify(authorService, times(1)).deleteById(EXPECTED_AUTHOR.getId());
    }

    @Test
    void getAuthor() throws Exception {
        given(authorService.getById(EXPECTED_AUTHOR.getId()))
                .willReturn(EXPECTED_AUTHOR);

        MockHttpServletRequestBuilder getAuthor = get("/authors/" + EXPECTED_AUTHOR.getId());
        mvc.perform(getAuthor)
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(EXPECTED_AUTHOR.getId())))
                .andExpect(content().string(containsString(EXPECTED_AUTHOR.getName())));

        verify(authorService, times(1)).getById(EXPECTED_AUTHOR.getId());
    }
}
