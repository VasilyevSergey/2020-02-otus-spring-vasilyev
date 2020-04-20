package com.otus.homework.service;

import com.otus.homework.dao.BookDao;
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
    private static final String BOOK_NOT_FOUND = "Книга с id '%d' не найдена";
    private final BookDao bookDao;
    private final AuthorService authorService;
    private final GenreService genreService;

    public BookServiceImpl(BookDao bookDao,
                           AuthorService authorService,
                           GenreService genreService) {
        this.bookDao = bookDao;
        this.authorService = authorService;
        this.genreService = genreService;
    }

    @Override
    public long count() {
        return bookDao.count();
    }

    @Override
    public void insert(String title, Long authorId, Long genreId) throws DataLoadingException {
        Author author = authorService.getById(authorId);
        Genre genre = genreService.getById(genreId);
        Book book = new Book(title, author, genre);
        try {
            bookDao.save(book);
        } catch (Exception e) {
            throw new DataLoadingException(String.format(BOOK_ALREADY_EXIST, book.toString()), e.getCause());
        }
    }

    @Override
    public Book getById(Long id) throws DataLoadingException {
        Optional<Book> book = bookDao.findById(id);
        if (book.isPresent()) {
            return book.get();
        } else {
            throw new DataLoadingException(String.format(BOOK_NOT_FOUND, id));
        }
    }

    @Override
    public void deleteById(Long id) throws DataLoadingException {
        if (!bookDao.existsById(id)) {
            throw new DataLoadingException(String.format(BOOK_NOT_FOUND, id));
        }
        bookDao.deleteById(id);
    }

    @Override
    public List<Book> getAll() {
        return bookDao.findAll();
    }

    @Override
    public void updateById(Long id, String newTitle, Long newAuthorId, Long newGenreId) throws DataLoadingException {
        Author newAuthor = authorService.getById(newAuthorId);
        Genre newGenre = genreService.getById(newGenreId);
        Book book = new Book(id, newTitle, newAuthor, newGenre);
        if (!bookDao.existsById(id)) {
            throw new DataLoadingException(String.format(BOOK_NOT_FOUND, id));
        }
        bookDao.save(book);
    }
}
