package com.otus.homework.controller;

import com.otus.homework.domain.Author;
import com.otus.homework.exception.DataLoadingException;
import com.otus.homework.service.AuthorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("/")
    public String listPage(Model model) {
        List<Author> authors = authorService.getAll();
        String newAuthorName = "";
        model.addAttribute("authors", authors)
                .addAttribute("newAuthorName", newAuthorName);
        return "author/list";
    }

    @GetMapping("/author/edit")
    public String editPage(@RequestParam("id") String id, Model model) throws DataLoadingException {
        Author author = authorService.getById(id);
        model.addAttribute("author", author);
        return "author/edit";
    }

    @PostMapping("/author/edit")
    public String editAuthor(Author author, Model model) {
        Author saved = authorService.updateById(author);
        model.addAttribute(saved);
        return "author/edit";
    }

    @PostMapping("/author/add")
    public String addAuthor(@RequestParam("newAuthorName") String newAuthorName, Model model) throws DataLoadingException {
        Author saved = authorService.insert(newAuthorName);
        List<Author> authors = authorService.getAll();
        model.addAttribute(saved)
                .addAttribute("authors", authors);
        return "author/list";
    }
}
