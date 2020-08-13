package com.otus.homework.restController;

import com.otus.homework.domain.Author;
import com.otus.homework.dto.AuthorDto;
import com.otus.homework.exception.DataLoadingException;
import com.otus.homework.service.AuthorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class AuthorRestController {

    private final AuthorService authorService;

    public AuthorRestController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("authors")
    public List<AuthorDto> getAllAuthors() {
        return authorService.getAll().stream().map(AuthorDto::toDto)
                .collect(Collectors.toList());
    }

    @PostMapping("authors")
    public void addAuthor(@RequestParam("newAuthorName") String newAuthorName) throws DataLoadingException {
        authorService.insert(newAuthorName);
    }

    @GetMapping("authors/{id}")
    public AuthorDto getAuthor(@PathVariable String id) throws DataLoadingException {
        return AuthorDto.toDto(authorService.getById(id));
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
