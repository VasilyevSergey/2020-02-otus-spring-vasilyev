package com.otus.homework.service;

import com.otus.homework.domain.Author;
import com.otus.homework.exception.DataLoadingException;
import com.otus.homework.repository.AuthorRepository;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.otus.homework.security.AuthoritiesConstants.ADMIN;
import static com.otus.homework.security.AuthoritiesConstants.USER;

@Service
public class AuthorServiceImpl implements AuthorService {
    private static final String ERROR_INSERT = "При добавлении автора '%s' произошла ошибка";
    private static final String AUTHOR_NOT_FOUND = "Автор с id '%s' не найден";
    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public long count() {
        return authorRepository.count();
    }

    @Secured({USER, ADMIN})
    @Override
    public Author insert(String name) throws DataLoadingException {
        Author author = new Author(name);
        try {
            return authorRepository.save(author);
        } catch (Exception e) {
            throw new DataLoadingException(String.format(ERROR_INSERT, name), e.getCause());
        }
    }

    @Secured({USER, ADMIN})
    @Override
    public Author getById(String id) throws DataLoadingException {
        return authorRepository.findById(id)
                .orElseThrow(() -> new DataLoadingException(String.format(AUTHOR_NOT_FOUND, id)));
    }

    @Secured({USER, ADMIN})
    @Override
    public void deleteById(String id) throws DataLoadingException {
        if (!authorRepository.existsById(id)) {
            throw new DataLoadingException(String.format(AUTHOR_NOT_FOUND, id));
        }
        authorRepository.deleteById(id);
    }

    @Secured({USER, ADMIN})
    @Override
    public List<Author> getAll() {
        return authorRepository.findAll();
    }

    @Secured({USER, ADMIN})
    @Override
    public Author updateById(Author author) {
        return authorRepository.save(author);
    }
}
