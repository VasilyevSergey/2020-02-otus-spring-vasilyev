package com.otus.homework.service;

import com.otus.homework.dao.AuthorRepository;
import com.otus.homework.domain.Author;
import com.otus.homework.exception.DataLoadingException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorServiceImpl implements AuthorService {
    private static final String ERROR_INSERT = "При добавлении автора '%s' произошла ошибка";
    private static final String AUTHOR_NOT_FOUND = "Автор с id '%d' не найден";
    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public long count() {
        return authorRepository.count();
    }

    @Override
    public void insert(String name) throws DataLoadingException {
        Author author = new Author(name);
        try {
            authorRepository.save(author);
        } catch (Exception e) {
            throw new DataLoadingException(String.format(ERROR_INSERT, name), e.getCause());
        }
    }

    @Override
    public Author getById(Long id) throws DataLoadingException {

        Optional<Author> author = authorRepository.findById(id);
        if (author.isPresent()) {
            return author.get();
        } else {
            throw new DataLoadingException(String.format(AUTHOR_NOT_FOUND, id));
        }
    }

    @Override
    public void deleteById(Long id) throws DataLoadingException {
        if (!authorRepository.existsById(id)) {
            throw new DataLoadingException(String.format(AUTHOR_NOT_FOUND, id));
        }
        authorRepository.deleteById(id);
    }

    @Override
    public List<Author> getAll() {
        return authorRepository.findAll();
    }
}
