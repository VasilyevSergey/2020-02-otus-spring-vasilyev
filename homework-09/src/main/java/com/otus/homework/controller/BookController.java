package com.otus.homework.controller;

import com.otus.homework.domain.Author;
import com.otus.homework.domain.Book;
import com.otus.homework.domain.Genre;
import com.otus.homework.exception.DataLoadingException;
import com.otus.homework.service.AuthorService;
import com.otus.homework.service.BookService;
import com.otus.homework.service.GenreService;
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
    private final GenreService genreService;

    public BookController(AuthorService authorService,
                          BookService bookService,
                          GenreService genreService) {
        this.authorService = authorService;
        this.bookService = bookService;
        this.genreService = genreService;
    }

    @GetMapping("/book/list-by-author")
    public String listPage(@RequestParam("id") String id, Model model) throws DataLoadingException {
        List<Book> books = bookService.getByAuthorId(id);
        String title = "";
        List<Genre> genres = genreService.getAll();
        String genreId = "";
        model.addAttribute("books", books)
                .addAttribute("author", authorService.getById(id))
                .addAttribute("title", title)
                .addAttribute("genreId", genreId)
                .addAttribute("genres", genres);
        return "book/list-by-author";
    }

    @GetMapping("/book/edit")
    public String editPage(@RequestParam("id") String id, Model model) throws DataLoadingException {
        Book book = bookService.getById(id);
        List<Author> authors = authorService.getAll();
        List<Genre> genres = genreService.getAll();
        model.addAttribute("book", book)
                .addAttribute("authors", authors)
                .addAttribute("genres", genres);
        return "book/edit";
    }

    @PostMapping("/book/edit")
    public String saveBook(@RequestParam String id,
                           @RequestParam String title,
                           @RequestParam String authorId,
                           @RequestParam String genreId,
                           Model model) throws DataLoadingException {
        Book book = new Book(id,
                title,
                authorService.getById(authorId),
                genreService.getById(genreId));
        Book saved = bookService.updateById(book);
        List<Author> authors = authorService.getAll();
        List<Genre> genres = genreService.getAll();
        model.addAttribute("book", saved)
                .addAttribute("authors", authors)
                .addAttribute("genres", genres);
        return "book/edit";
    }

    @PostMapping("/book/delete")
    public String deleteBook(@RequestParam String id,
                             Model model) throws DataLoadingException {
        Book bookToDelete = bookService.getById(id);
        Author author = bookToDelete.getAuthor();
        bookService.deleteById(id);
        List<Book> books = bookService.getByAuthorId(author.getId());
        model.addAttribute("books", books)
                .addAttribute("author", author);
        return "book/list-by-author";
    }

    @PostMapping("/book/add")
    public String addBook(@RequestParam("title") String title,
                          @RequestParam("genreId") String genreId,
                          @RequestParam("authorId") String authorId,
                          Model model) throws DataLoadingException {
        Book saved = bookService.insert(title, authorId, genreId);
        List<Book> books = bookService.getByAuthorId(authorId);
        List<Genre> genres = genreService.getAll();
        model.addAttribute(saved)
                .addAttribute("books", books)
                .addAttribute("author", authorService.getById(authorId))
                .addAttribute("genres", genres);
        return "book/list-by-author";
    }

}
