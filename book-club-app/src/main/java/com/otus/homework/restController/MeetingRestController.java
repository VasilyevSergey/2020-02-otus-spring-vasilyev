package com.otus.homework.restController;

import com.otus.homework.domain.BookClub;
import com.otus.homework.dto.MeetingDto;
import com.otus.homework.exception.DataLoadingException;
import com.otus.homework.service.MeetingService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MeetingRestController {
    private final MeetingService meetingService;

    public MeetingRestController(MeetingService meetingService) {
        this.meetingService = meetingService;
    }

    @GetMapping("api/v1/meeting")
    public List<MeetingDto> getAll() {
        return meetingService.getAll();
    }

    @PostMapping("api/v1/meeting")
    public void create(@RequestParam("meeting") MeetingDto meetingDto) throws DataLoadingException {
        meetingService.create(meetingDto);
    }

    @GetMapping("api/v1/meeting/{id}")
    public MeetingDto get(@PathVariable String id) throws DataLoadingException {
        return meetingService.getById(id);
    }

    @PutMapping("api/v1/meeting/{id}")
    public void edit(@RequestParam("meeting") MeetingDto meetingDto) throws DataLoadingException {
        meetingService.updateById(meetingDto);
    }

    @DeleteMapping("api/v1/meeting/{id}")
    public void delete(@PathVariable String id) throws DataLoadingException {
        meetingService.deleteById(id);
    }

    @GetMapping("api/v1/meeting/find-all-by-book-club")
    public List<MeetingDto> findAllByBookClub(@RequestParam("bookClub") BookClub bookClub) {
        return meetingService.findAllByBookClub(bookClub);
    }

    @GetMapping("api/v1/meeting/find-all-by-theme")
    public List<MeetingDto> findAllByTheme(@RequestParam("theme") String theme) {
        return meetingService.findAllByTheme(theme);
    }

    @GetMapping("api/v1/meeting/join/{id}")
    public MeetingDto joinToClub(@PathVariable("id") String id) throws DataLoadingException {
        return meetingService.joinToMeeting(id);
    }
}
