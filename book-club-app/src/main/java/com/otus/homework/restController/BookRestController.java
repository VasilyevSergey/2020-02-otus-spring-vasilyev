package com.otus.homework.restController;

import com.otus.homework.domain.Author;
import com.otus.homework.domain.Book;
import com.otus.homework.exception.DataLoadingException;
import com.otus.homework.service.AuthorService;
import com.otus.homework.service.BookService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookRestController {

    private final AuthorService authorService;
    private final BookService bookService;

    public BookRestController(AuthorService authorService,
                              BookService bookService) {
        this.authorService = authorService;
        this.bookService = bookService;
    }

    @GetMapping("book")
    public List<Book> getAll() {
        return bookService.getAll();
    }

    @PostMapping("book")
    public void add(@RequestParam("title") String title,
                    @RequestParam("authorId") String authorId) throws DataLoadingException {
        bookService.insert(title, authorId);
    }

    @GetMapping("book/{id}")
    public Book get(@PathVariable String id) throws DataLoadingException {
        return bookService.getById(id);
    }

    @PutMapping("book/{id}")
    public void edit(@PathVariable("id") String id,
                     @RequestParam("title") String title,
                     @RequestParam("authorId") String authorId) throws DataLoadingException {
        Author author = authorService.getById(authorId);
        Book updatedBook = new Book(id, title, author);
        bookService.updateById(updatedBook);
    }

    @DeleteMapping("book/{id}")
    public void delete(@PathVariable String id) throws DataLoadingException {
        bookService.deleteById(id);
    }

    @GetMapping("book/by-author/{id}")
    public List<Book> getAllByAuthorId(@PathVariable String id) throws DataLoadingException {
        return bookService.getAllByAuthorId(id);
    }
}
