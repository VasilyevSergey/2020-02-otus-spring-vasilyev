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

    @GetMapping("api/author/get-all")
    public List<AuthorDto> getAllAuthors() {
        return authorService.getAll().stream().map(AuthorDto::toDto)
                .collect(Collectors.toList());
    }

    @PostMapping("api/author/add")
    public void addAuthor(@RequestParam("newAuthorName") String newAuthorName) throws DataLoadingException {
        authorService.insert(newAuthorName);
    }

    @GetMapping("api/author/get/{id}")
    public AuthorDto getAuthor(@PathVariable String id) throws DataLoadingException {
        return AuthorDto.toDto(authorService.getById(id));
    }

    @PostMapping("api/author/edit")
    public void editAuthor(@RequestParam("id") String id,
                           @RequestParam("name") String name) {
        Author updatedAuthor = new Author(id, name);
        authorService.updateById(updatedAuthor);
    }

    @PostMapping("api/author/delete/{id}")
    public void deleteAuthor(@PathVariable String id) throws DataLoadingException {
        authorService.deleteById(id);
    }
}
