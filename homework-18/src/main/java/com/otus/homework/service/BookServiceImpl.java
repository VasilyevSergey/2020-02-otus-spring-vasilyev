package com.otus.homework.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.otus.homework.repository.BookRepository;
import com.otus.homework.domain.Author;
import com.otus.homework.domain.Book;
import com.otus.homework.domain.Genre;
import com.otus.homework.exception.DataLoadingException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {
    private static final String BOOK_ALREADY_EXIST = "Книга %s уже существует";
    private static final String BOOK_NOT_FOUND = "Книга с id '%s' не найдена";
    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final GenreService genreService;

    public BookServiceImpl(BookRepository bookRepository,
                           AuthorService authorService,
                           GenreService genreService) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
        this.genreService = genreService;
    }

    @HystrixCommand(commandKey = "count", fallbackMethod = "countFallback")
    @Override
    public long count() {
        return bookRepository.count();
    }

    public long countFallback() {
        return 0L;
    }

    @Override
    public Book insert(String title, String authorId, String genreId) throws DataLoadingException {
        Author author = authorService.getById(authorId);
        Genre genre = genreService.getById(genreId);
        Book book = new Book(title, author, genre);
        try {
            return bookRepository.save(book);
        } catch (Exception e) {
            throw new DataLoadingException(String.format(BOOK_ALREADY_EXIST, book.toString()), e.getCause());
        }
    }

    @Override
    public Book getById(String id) throws DataLoadingException {
        Optional<Book> book = bookRepository.findById(id);
        if (book.isPresent()) {
            return book.get();
        } else {
            System.out.println("!!!");
            throw new DataLoadingException(String.format(BOOK_NOT_FOUND, id));
        }
    }

    @Override
    public void deleteById(String id) throws DataLoadingException {
        if (!bookRepository.existsById(id)) {
            throw new DataLoadingException(String.format(BOOK_NOT_FOUND, id));
        }
        bookRepository.deleteById(id);
    }

    @HystrixCommand(commandKey = "getAll", fallbackMethod = "getDefaultBookList")
    @Override
    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    public List<Book> getDefaultBookList() {
        return Collections.emptyList();
    }

    @Override
    public Book updateById(Book book) throws DataLoadingException {
        if (!bookRepository.existsById(book.getId())) {
            throw new DataLoadingException(String.format(BOOK_NOT_FOUND, book.getId()));
        }
        return bookRepository.save(book);
    }

    @HystrixCommand(commandKey = "getByAuthorId", fallbackMethod = "getByAuthorIdFallback")
    @Override
    public List<Book> getByAuthorId(String id) throws DataLoadingException {
        return bookRepository.findAllByAuthor(authorService.getById(id));
    }

    public List<Book> getByAuthorIdFallback(String id) {
        return Collections.emptyList();
    }
}