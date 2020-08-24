package com.otus.homework.service;

import com.otus.homework.domain.BookClub;
import com.otus.homework.domain.Meeting;
import com.otus.homework.domain.User;
import com.otus.homework.dto.MeetingDto;
import com.otus.homework.exception.DataLoadingException;
import com.otus.homework.repository.MeetingRepository;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
    private final BookClubService bookClubService;

    public MeetingServiceImpl(
            UserService userService,
            MeetingRepository meetingRepository,
            BookClubService bookClubService) {
        this.userService = userService;
        this.meetingRepository = meetingRepository;
        this.bookClubService = bookClubService;
    }

    @Override
    public MeetingDto create(MeetingDto meetingDto) throws DataLoadingException {
        User admin = userService.getCurrentUser();
        Meeting meeting = meetingDto.toEntity();
        meeting.setAdmin(admin);
        meeting.setParticipantList(List.of(admin));

        try {
            return MeetingDto.toDto(meetingRepository.save(meeting));
        } catch (Exception e) {
            throw new DataLoadingException(String.format(ERROR_INSERT, meetingDto.getTheme()), e.getCause());
        }
    }

    @Secured({USER, ADMIN})
    @Override
    public MeetingDto getById(String id) throws DataLoadingException {
         Meeting meeting = meetingRepository.findById(id)
                .orElseThrow(() -> new DataLoadingException(String.format(MEETING_NOT_FOUND, id)));
        return MeetingDto.toDto(meeting);
    }

    @Secured({USER, ADMIN})
    @Override
    public void deleteById(String id) throws DataLoadingException {
        MeetingDto meetingDto = getById(id);
        if (meetingDto.getAdmin().equals(userService.getCurrentUser())) {
            meetingRepository.deleteById(id);
        } else {
            throw new DataLoadingException(String.format(CANT_DELETE_MEETING_BECAUSE_NOT_ADMIN, meetingDto.getTheme()));
        }
    }

    @Secured({USER, ADMIN})
    @Override
    public List<MeetingDto> getAll() {
        return meetingRepository.findAll()
                                .stream()
                                .map(MeetingDto::toDto)
                                .collect(Collectors.toList());
    }

    @Secured({USER, ADMIN})
    @Override
    public List<MeetingDto> findAllByBookClub(String id) throws DataLoadingException {
        BookClub bookClub = bookClubService.getById(id);
        return meetingRepository.findAllByBookClub(bookClub)
                                .stream()
                                .map(MeetingDto::toDto)
                                .collect(Collectors.toList());
    }

    @Secured({USER, ADMIN})
    @Override
    public List<MeetingDto> findAllByTheme(String theme) {
        return meetingRepository.findAllByTheme(theme)
                                .stream()
                                .map(MeetingDto::toDto)
                                .collect(Collectors.toList());
    }

    @Secured({USER, ADMIN})
    @Override
    public MeetingDto updateById(MeetingDto meetingDto) throws DataLoadingException {
        Meeting meeting = meetingRepository.findById(meetingDto.getId())
                .orElseThrow(() -> new DataLoadingException(String.format(MEETING_NOT_FOUND, meetingDto.getId())));

        meeting.setTheme(meetingDto.getTheme());
        meeting.setMeetingsDateTime(meetingDto.getMeetingsDateTime());
        meeting.setDescription(meetingDto.getDescription());
        meeting.setBookList(meetingDto.getBookList());
        meeting.setAddress(meetingDto.getAddress());
        meeting.setLongitude(meetingDto.getLongitude());
        meeting.setLatitude(meetingDto.getLatitude());

        return MeetingDto.toDto(meetingRepository.save(meeting));
    }

    @Secured({USER, ADMIN})
    @Override
    public MeetingDto joinToMeeting(String id) throws DataLoadingException {
        Meeting meeting = meetingRepository.findById(id)
                .orElseThrow(() -> new DataLoadingException(String.format(MEETING_NOT_FOUND, id)));

        User currentUser = userService.getCurrentUser();

        List<User> participantList = meeting.getParticipantList();
        if (!participantList.contains(currentUser)) {
            participantList.add(currentUser);
        } else {
            throw new DataLoadingException(String.format(YOU_HAVE_ALREADY_JOINED_THE_MEETING, meeting.getTheme()));
        }
        return MeetingDto.toDto(meetingRepository.save(meeting));
    }
}
