package com.otus.homework.service;

import com.otus.homework.domain.Author;
import com.otus.homework.domain.Book;
import com.otus.homework.exception.DataLoadingException;
import com.otus.homework.repository.BookRepository;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.otus.homework.security.AuthoritiesConstants.ADMIN;
import static com.otus.homework.security.AuthoritiesConstants.USER;

@Service
public class BookServiceImpl implements BookService {
    private static final String BOOK_ALREADY_EXIST = "Книга %s уже существует";
    private static final String BOOK_NOT_FOUND = "Книга с id '%s' не найдена";
    private final BookRepository bookRepository;
    private final AuthorService authorService;

    public BookServiceImpl(BookRepository bookRepository,
                           AuthorService authorService) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
    }

    @Secured({USER, ADMIN})
    @Override
    public long count() {
        return bookRepository.count();
    }

    @Secured({USER, ADMIN})
    @Override
    public Book insert(String title, String authorId) throws DataLoadingException {
        Author author = authorService.getById(authorId);
        Book book = new Book(title, author);
        try {
            return bookRepository.save(book);
        } catch (Exception e) {
            throw new DataLoadingException(String.format(BOOK_ALREADY_EXIST, book.toString()), e.getCause());
        }
    }

    @Secured({USER, ADMIN})
    @Override
    public Book getById(String id) throws DataLoadingException {
        Optional<Book> book = bookRepository.findById(id);
        if (book.isPresent()) {
            return book.get();
        } else {
            throw new DataLoadingException(String.format(BOOK_NOT_FOUND, id));
        }
    }

    @Secured({USER, ADMIN})
    @Override
    public void deleteById(String id) throws DataLoadingException {
        if (!bookRepository.existsById(id)) {
            throw new DataLoadingException(String.format(BOOK_NOT_FOUND, id));
        }
        bookRepository.deleteById(id);
    }

    @Secured({USER, ADMIN})
    @Override
    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    @Secured({USER, ADMIN})
    @Override
    public Book updateById(Book book) throws DataLoadingException {
        if (!bookRepository.existsById(book.getId())) {
            throw new DataLoadingException(String.format(BOOK_NOT_FOUND, book.getId()));
        }
        return bookRepository.save(book);
    }

    @Secured({USER, ADMIN})
    public List<Book> getAllByAuthorId(String id) throws DataLoadingException {
        return bookRepository.findAllByAuthor(authorService.getById(id));
    }
}
