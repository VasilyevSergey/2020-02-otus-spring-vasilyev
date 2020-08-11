package com.otus.homework.service;

import com.otus.homework.repository.GenreRepository;
import com.otus.homework.domain.Genre;
import com.otus.homework.exception.DataLoadingException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GenreServiceImpl implements GenreService {
    private static final String ERROR_INSERT = "При добавлении жанра '%s' произошла ошибка";
    private static final String GENRE_NOT_FOUND = "Жанр с id '%s' не найден";
    private final GenreRepository genreRepository;

    public GenreServiceImpl(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @Override
    public long count() {
        return genreRepository.count();
    }

    @Override
    public void insert(String name) throws DataLoadingException {
        Genre genre = new Genre(name);
        try {
            genreRepository.save(genre);
        } catch (Exception e) {
            throw new DataLoadingException(String.format(ERROR_INSERT, name), e.getCause());
        }
    }

    @Override
    public Genre getById(String id) throws DataLoadingException {
        Optional<Genre> genre = genreRepository.findById(id);
        if (genre.isPresent()) {
            return genre.get();
        } else {
            throw new DataLoadingException(String.format(GENRE_NOT_FOUND, id));
        }
    }

    @Override
    public void deleteById(String id) throws DataLoadingException {
        if (!genreRepository.existsById(id)) {
            throw new DataLoadingException(String.format(GENRE_NOT_FOUND, id));
        }
        genreRepository.deleteById(id);
    }

    @Override
    public List<Genre> getAll() {
        return genreRepository.findAll();
    }
}
