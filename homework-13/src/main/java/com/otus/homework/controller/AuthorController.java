package com.otus.homework.controller;

import com.otus.homework.domain.Author;
import com.otus.homework.exception.DataLoadingException;
import com.otus.homework.service.AuthorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/author/edit/{id}")
    public String editPage(@PathVariable String id, Model model) throws DataLoadingException {
        Author author = authorService.getById(id);
        model.addAttribute("author", author);
        return "author/edit";
    }

    @PostMapping("/author/edit")
    public String editAuthor(Author author, Model model) {
        authorService.updateById(author);
        return "redirect:/";
    }

    @PostMapping("/author/add")
    public String addAuthor(@RequestParam("newAuthorName") String newAuthorName, Model model) throws DataLoadingException {
        authorService.insert(newAuthorName);
        return "redirect:/";
    }

    @PostMapping("/author/delete/{id}")
    public String deleteAuthor(@PathVariable String id, Model model) throws DataLoadingException {
        authorService.deleteById(id);
        return "redirect:/";
    }

    @GetMapping("/403")
    public String accessDenied() {
        return "errors/403";
    }

    @RequestMapping("/405")
    public String methodNotAllowed() {
        return "errors/405";
    }
}
