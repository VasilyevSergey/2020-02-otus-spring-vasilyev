package com.otus.homework.service;

import com.otus.homework.dao.AuthorDao;
import com.otus.homework.domain.Author;
import com.otus.homework.exception.DataLoadingException;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {
    private static final String ERROR_INSERT = "При добавлении автора '%s' произошла ошибка";
    private static final String AUTHOR_NOT_FOUND = "Автор с id '%d' не найден";
    private final AuthorDao authorDao;

    public AuthorServiceImpl(AuthorDao authorDao) {
        this.authorDao = authorDao;
    }

    @Override
    public int count() {
        return authorDao.count();
    }

    @Override
    public void insert(String name) throws DataLoadingException {
        Author author = new Author(null, name);
        try {
            authorDao.insert(author);
        } catch (DataAccessException e) {
            throw new DataLoadingException(String.format(ERROR_INSERT, name), e.getCause());
        }
    }

    @Override
    public Author getById(Long id) throws DataLoadingException {
        try {
            return authorDao.getById(id);
        } catch (DataAccessException e) {
            throw new DataLoadingException(String.format(AUTHOR_NOT_FOUND, id), e.getCause());
        }
    }

    @Override
    public void deleteById(Long id) throws DataLoadingException {
        try {
            authorDao.deleteById(id);
        } catch (DataAccessException e) {
            throw new DataLoadingException(String.format(AUTHOR_NOT_FOUND, id), e.getCause());
        }
    }

    @Override
    public List<Author> getAll() {
        return authorDao.getAll();
    }
}
