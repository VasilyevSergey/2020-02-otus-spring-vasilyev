package com.otus.homework.service;

import com.otus.homework.domain.Book;
import com.otus.homework.domain.BookClub;
import com.otus.homework.domain.Meeting;
import com.otus.homework.domain.User;
import com.otus.homework.exception.DataLoadingException;
import com.otus.homework.repository.MeetingRepository;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.otus.homework.security.AuthoritiesConstants.ADMIN;
import static com.otus.homework.security.AuthoritiesConstants.USER;

@Service
public class MeetingServiceImpl implements MeetingService {
    private static final String ERROR_INSERT = "При создании встречи '%s' произошла ошибка";
    private static final String MEETING_NOT_FOUND = "Встреча с id '%s' не найдена";
    private static final String CANT_DELETE_MEETING_BECAUSE_NOT_ADMIN =
            "Вы не можете отменить встречу %s, т.к. не являетесь её оранизатором";
    private static final String YOU_HAVE_ALREADY_JOINED_THE_MEETING = "Вы уже являетесь участником встречи  '%s'.";


    private final UserService userService;
    private final MeetingRepository meetingRepository;

    public MeetingServiceImpl(
            UserService userService,
            MeetingRepository meetingRepository) {
        this.userService = userService;
        this.meetingRepository = meetingRepository;
    }

    @Override
    public Meeting create(String theme,
                          LocalDateTime meetingsDateTime,
                          String description,
                          List<Book> bookList,
                          BookClub bookClub,
                          String address,
                          Double longitude,
                          Double latitude) throws DataLoadingException {
        User admin = userService.getCurrentUser();
        Meeting meeting = new Meeting(theme,
                meetingsDateTime,
                description,
                bookList,
                bookClub,
                address,
                longitude,
                latitude,
                admin,
                List.of(admin));

        try {
            return meetingRepository.save(meeting);
        } catch (Exception e) {
            throw new DataLoadingException(String.format(ERROR_INSERT, theme), e.getCause());
        }
    }

    @Secured({USER, ADMIN})
    @Override
    public Meeting getById(String id) throws DataLoadingException {
        Optional<Meeting> meeting = meetingRepository.findById(id);
        if (meeting.isPresent()) {
            return meeting.get();
        } else {
            throw new DataLoadingException(String.format(MEETING_NOT_FOUND, id));
        }
    }

    @Secured({USER, ADMIN})
    @Override
    public void deleteById(String id) throws DataLoadingException {
        Meeting meeting = getById(id);
        if (meeting.getAdmin().equals(userService.getCurrentUser())) {
            meetingRepository.deleteById(id);
        } else {
            throw new DataLoadingException(String.format(CANT_DELETE_MEETING_BECAUSE_NOT_ADMIN, meeting.getTheme()));
        }
    }

    @Secured({USER, ADMIN})
    @Override
    public List<Meeting> getAll() {
        return meetingRepository.findAll();
    }

    @Secured({USER, ADMIN})
    @Override
    public List<Meeting> findAllByBookClub(BookClub bookClub) {
        return meetingRepository.findAllByBookClub(bookClub);
    }

    @Secured({USER, ADMIN})
    @Override
    public List<Meeting> findAllByTheme(String theme) {
        return meetingRepository.findAllByTheme(theme);
    }

    @Secured({USER, ADMIN})
    @Override
    public Meeting updateById(String id,
                              String theme,
                              LocalDateTime meetingsDateTime,
                              String description,
                              List<Book> bookList,
                              String address,
                              Double longitude,
                              Double latitude) throws DataLoadingException {
        Meeting meeting = getById(id);

        meeting.setTheme(theme);
        meeting.setMeetingsDateTime(meetingsDateTime);
        meeting.setDescription(description);
        meeting.setBookList(bookList);
        meeting.setAddress(address);
        meeting.setLongitude(longitude);
        meeting.setLatitude(latitude);

        return meetingRepository.save(meeting);
    }

    @Secured({USER, ADMIN})
    @Override
    public Meeting joinToMeeting(String id) throws DataLoadingException {
        Meeting meeting = getById(id);
        User currentUser = userService.getCurrentUser();

        List<User> participantList = meeting.getParticipantList();
        if (!participantList.contains(currentUser)) {
            participantList.add(currentUser);
        } else {
            throw new DataLoadingException(String.format(YOU_HAVE_ALREADY_JOINED_THE_MEETING, meeting.getTheme()));
        }
        return meetingRepository.save(meeting);
    }
}
