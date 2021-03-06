package com.otus.homework.service;

import com.otus.homework.dao.BookDao;
import com.otus.homework.domain.Author;
import com.otus.homework.domain.Book;
import com.otus.homework.domain.Genre;
import com.otus.homework.exception.DataLoadingException;
import org.springframework.dao.DataAccessException;
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
    public int count() {
        return bookDao.count();
    }

    @Override
    public void insert(String title, Long authorId, Long genreId) throws DataLoadingException {
        Author author = authorService.getById(authorId);
        Genre genre = genreService.getById(genreId);
        Book book = new Book(null, title, author, genre);
        try {
            bookDao.insert(book);
        } catch (DataAccessException e) {
            throw new DataLoadingException(String.format(BOOK_ALREADY_EXIST, book.toString()), e.getCause());
        }
    }

    @Override
    public Book getById(Long id) throws DataLoadingException {
        Optional<Book> book =  bookDao.getById(id);
        if (book.isPresent()) {
            return book.get();
        } else {
            throw new DataLoadingException(String.format(BOOK_NOT_FOUND, id));
        }
    }

    @Override
    public void deleteById(Long id) throws DataLoadingException {
        try {
            bookDao.deleteById(id);
        } catch (DataAccessException e) {
            throw new DataLoadingException(String.format(BOOK_NOT_FOUND, id), e.getCause());
        }
    }

    @Override
    public List<Book> getAll() {
        return bookDao.getAll();
    }

    @Override
    public void updateById(Long id, String newTitle, Long newAuthorId, Long newGenreId) throws DataLoadingException {
        Author newAuthor = authorService.getById(newAuthorId);
        Genre newGenre = genreService.getById(newGenreId);
        Book book = new Book(id, newTitle, newAuthor, newGenre);

        int numberOfRowsAffected = bookDao.update(book);
        if (numberOfRowsAffected == 0) {
            throw new DataLoadingException(String.format(BOOK_NOT_FOUND, id), new Exception().getCause());
        }
    }
}
