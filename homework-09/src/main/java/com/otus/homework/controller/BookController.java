package com.otus.homework.controller;

import com.otus.homework.domain.Author;
import com.otus.homework.domain.Book;
import com.otus.homework.exception.DataLoadingException;
import com.otus.homework.service.AuthorService;
import com.otus.homework.service.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class BookController {

    private final AuthorService authorService;
    private final BookService bookService;

    public BookController(AuthorService authorService,
                          BookService bookService) {
        this.authorService = authorService;
        this.bookService = bookService;
    }

    @GetMapping("/book/list-by-author")
    public String listPage(@RequestParam("id") String id, Model model) throws DataLoadingException {
        List<Book> books = bookService.getByAuthorId(id);
        model.addAttribute("books", books)
                .addAttribute("author", authorService.getById(id));
        return "book/list-by-author";
    }

    @GetMapping("/book/edit")
    public String editPage(@RequestParam("id") String id, Model model) throws DataLoadingException {
        Book book = bookService.getById(id);
        List<Author> authors = authorService.getAll();
        model.addAttribute("book", book)
                .addAttribute("authors", authors);
        return "book/edit";
    }

    @PostMapping("/book/edit")
    public String saveBook(Book book, Model model) throws DataLoadingException {
        Book saved = bookService.updateById(book);
        model.addAttribute(saved);
        return "book/edit";
    }
}
