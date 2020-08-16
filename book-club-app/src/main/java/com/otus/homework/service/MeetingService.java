package com.otus.homework.service;

import com.otus.homework.domain.Book;
import com.otus.homework.domain.BookClub;
import com.otus.homework.domain.Meeting;
import com.otus.homework.exception.DataLoadingException;

import java.time.LocalDateTime;
import java.util.List;

public interface MeetingService {

    Meeting create(String theme,
                   LocalDateTime meetingsDateTime,
                   String description,
                   List<Book> bookList,
                   BookClub bookClub,
                   String address,
                   Double longitude,
                   Double latitude) throws DataLoadingException;

    Meeting getById(String id) throws DataLoadingException;

    void deleteById(String id) throws DataLoadingException;

    List<Meeting> getAll();

    Meeting updateById(String id,
                       String theme,
                       LocalDateTime meetingsDateTime,
                       String description,
                       List<Book> bookList,
                       String address,
                       Double longitude,
                       Double latitude) throws DataLoadingException;

    List<Meeting> findAllByBookClub(BookClub name);

    List<Meeting> findAllByTheme(String theme);

    Meeting joinToMeeting(String id) throws DataLoadingException;
}
