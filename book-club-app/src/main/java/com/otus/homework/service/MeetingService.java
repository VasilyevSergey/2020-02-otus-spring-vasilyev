package com.otus.homework.service;

import com.otus.homework.dto.MeetingDto;
import com.otus.homework.exception.DataLoadingException;

import java.util.List;

public interface MeetingService {

    MeetingDto create(MeetingDto meetingDto) throws DataLoadingException;

    MeetingDto getById(String id) throws DataLoadingException;

    void deleteById(String id) throws DataLoadingException;

    List<MeetingDto> getAll();

    MeetingDto updateById(MeetingDto meetingDto) throws DataLoadingException;

    List<MeetingDto> findAllByBookClub(String id) throws DataLoadingException;

    List<MeetingDto> findAllByTheme(String theme);

    MeetingDto joinToMeeting(String id) throws DataLoadingException;
}
