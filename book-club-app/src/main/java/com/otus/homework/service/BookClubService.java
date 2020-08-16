package com.otus.homework.service;

import com.otus.homework.domain.Author;
import com.otus.homework.domain.BookClub;
import com.otus.homework.domain.User;
import com.otus.homework.exception.DataLoadingException;

import java.util.List;

public interface BookClubService {
    BookClub create(String name,
                    String mainTheme) throws DataLoadingException;

    BookClub getById(String id) throws DataLoadingException;

    List<BookClub> findAllByName(String name);

    void deleteById(String id) throws DataLoadingException;

    List<BookClub> getAll();

    BookClub updateById(String id, String name, String mainTheme) throws DataLoadingException;

    BookClub joinToClub(String id) throws DataLoadingException;
}
