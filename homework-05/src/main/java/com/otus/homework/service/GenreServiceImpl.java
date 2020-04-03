package com.otus.homework.service;

import com.otus.homework.dao.GenreDao;
import com.otus.homework.domain.Genre;
import com.otus.homework.exception.DataLoadingException;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreServiceImpl implements GenreService {
    private static final String GENRE_ALREADY_EXIST = "Жанр с id '%d' уже существует";
    private static final String GENRE_NOT_FOUND = "Жанр с id '%d' не найден";
    private final GenreDao genreDao;

    public GenreServiceImpl(GenreDao genreDao) {
        this.genreDao = genreDao;
    }

    @Override
    public int count() {
        return genreDao.count();
    }

    @Override
    public void insert(Long id, String name) throws DataLoadingException {
        Genre genre = new Genre(id, name);
        try {
            genreDao.insert(genre);
        } catch (DataAccessException e) {
            throw new DataLoadingException(String.format(GENRE_ALREADY_EXIST, id), e.getCause());
        }
    }

    @Override
    public Genre getById(Long id) throws DataLoadingException {
        try {
            return genreDao.getById(id);
        } catch (DataAccessException e) {
            throw new DataLoadingException(String.format(GENRE_NOT_FOUND, id), e.getCause());
        }
    }

    @Override
    public void deleteById(Long id) throws DataLoadingException {
        try {
            genreDao.deleteById(id);
        } catch (DataAccessException e) {
            throw new DataLoadingException(String.format(GENRE_NOT_FOUND, id), e.getCause());
        }
    }

    @Override
    public List<Genre> getAll() {
        return genreDao.getAll();
    }
}
