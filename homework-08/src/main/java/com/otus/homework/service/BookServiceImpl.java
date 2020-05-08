package com.otus.homework.service;

import com.otus.homework.dao.BookRepository;
import com.otus.homework.domain.Author;
import com.otus.homework.domain.Book;
import com.otus.homework.domain.Genre;
import com.otus.homework.exception.DataLoadingException;
import org.springframework.stereotype.Service;

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

    @Override
    public long count() {
        return bookRepository.count();
    }

    @Override
    public void insert(String title, String authorId, String genreId) throws DataLoadingException {
        Author author = authorService.getById(authorId);
        Genre genre = genreService.getById(genreId);
        Book book = new Book(title, author, genre);
        try {
            bookRepository.save(book);
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

    @Override
    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    @Override
    public void updateById(String id, String newTitle, String newAuthorId, String newGenreId) throws DataLoadingException {
        Author newAuthor = authorService.getById(newAuthorId);
        Genre newGenre = genreService.getById(newGenreId);
        Book book = new Book(id, newTitle, newAuthor, newGenre);
        if (!bookRepository.existsById(id)) {
            throw new DataLoadingException(String.format(BOOK_NOT_FOUND, id));
        }
        bookRepository.save(book);
    }
}
