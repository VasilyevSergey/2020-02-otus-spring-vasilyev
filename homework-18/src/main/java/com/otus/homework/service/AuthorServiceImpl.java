package com.otus.homework.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.otus.homework.domain.Author;
import com.otus.homework.exception.DataLoadingException;
import com.otus.homework.repository.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class AuthorServiceImpl implements AuthorService {
    private static final String ERROR_INSERT = "При добавлении автора '%s' произошла ошибка";
    private static final String AUTHOR_NOT_FOUND = "Автор с id '%s' не найден";
    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @HystrixCommand(commandKey = "count", fallbackMethod = "countFallback")
    @Override
    public long count() {
        return authorRepository.count();
    }

    public long countFallback() {
        return 0L;
    }

    @Override
    public Author insert(String name) throws DataLoadingException {
        Author author = new Author(name);
        try {
            return authorRepository.save(author);
        } catch (Exception e) {
            throw new DataLoadingException(String.format(ERROR_INSERT, name), e.getCause());
        }
    }

    @Override
    public Author getById(String id) throws DataLoadingException {
        Optional<Author> author = authorRepository.findById(id);
        if (author.isPresent()) {
            return author.get();
        } else {
            throw new DataLoadingException(String.format(AUTHOR_NOT_FOUND, id));
        }
    }

    @Override
    public void deleteById(String id) throws DataLoadingException {
        if (!authorRepository.existsById(id)) {
            throw new DataLoadingException(String.format(AUTHOR_NOT_FOUND, id));
        }
        authorRepository.deleteById(id);
    }

    @HystrixCommand(commandKey = "getAllAuthors", fallbackMethod = "getDefaultAuthorList")
    @Override
    public List<Author> getAll() {
        return authorRepository.findAll();
    }

    public List<Author> getDefaultAuthorList() {
        return Collections.emptyList();
    }

    @HystrixCommand(commandKey = "updateById", fallbackMethod = "updateByIdFallback")
    @Override
    public Author updateById(Author author) {
        return authorRepository.save(author);
    }

    public Author updateByIdFallback(Author author) {
        return author;
    }
}
