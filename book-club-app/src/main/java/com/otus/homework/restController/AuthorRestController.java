package com.otus.homework.restController;

import com.otus.homework.domain.Author;
import com.otus.homework.exception.DataLoadingException;
import com.otus.homework.service.AuthorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AuthorRestController {

    private final AuthorService authorService;

    public AuthorRestController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("authors")
    public List<Author> getAllAuthors() {
        return authorService.getAll();
    }

    @PostMapping("authors")
    public void addAuthor(@RequestParam("newAuthorName") String newAuthorName) throws DataLoadingException {
        authorService.insert(newAuthorName);
    }

    @GetMapping("authors/{id}")
    public Author getAuthor(@PathVariable String id) throws DataLoadingException {
        return authorService.getById(id);
    }

    @PutMapping("authors/{id}")
    public void editAuthor(@PathVariable("id") String id,
                           @RequestParam("name") String name) {
        Author updatedAuthor = new Author(id, name);
        authorService.updateById(updatedAuthor);
    }

    @DeleteMapping("authors/{id}")
    public void deleteAuthor(@PathVariable String id) throws DataLoadingException {
        authorService.deleteById(id);
    }
}
