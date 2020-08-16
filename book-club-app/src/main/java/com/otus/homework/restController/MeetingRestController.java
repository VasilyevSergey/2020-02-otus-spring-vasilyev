package com.otus.homework.restController;

import com.otus.homework.domain.Book;
import com.otus.homework.domain.BookClub;
import com.otus.homework.domain.Meeting;
import com.otus.homework.exception.DataLoadingException;
import com.otus.homework.service.MeetingService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class MeetingRestController {
    private final MeetingService meetingService;

    public MeetingRestController(MeetingService meetingService) {
        this.meetingService = meetingService;
    }

    @GetMapping("meeting")
    public List<Meeting> getAllMeetings() {
        return meetingService.getAll();
    }

    @PostMapping("meeting")
    public void createMeeting(@RequestParam("theme") String theme,
                              @RequestParam("meetingsDateTime") LocalDateTime meetingsDateTime,
                              @RequestParam("description") String description,
                              @RequestParam("bookList") List<Book> bookList,
                              @RequestParam("bookClub") BookClub bookClub,
                              @RequestParam("address") String address,
                              @RequestParam("longitude") Double longitude,
                              @RequestParam("latitude") Double latitude) throws DataLoadingException {
        meetingService.create(
                theme,
                meetingsDateTime,
                description,
                bookList,
                bookClub,
                address,
                longitude,
                latitude);
    }

    @GetMapping("meeting/{id}")
    public Meeting getMeeting(@PathVariable String id) throws DataLoadingException {
        return meetingService.getById(id);
    }

    @PutMapping("meeting/{id}")
    public void editMeeting(@PathVariable("id") String id,
                            @RequestParam("theme") String theme,
                            @RequestParam("meetingsDateTime") LocalDateTime meetingsDateTime,
                            @RequestParam("description") String description,
                            @RequestParam("bookList") List<Book> bookList,
                            @RequestParam("address") String address,
                            @RequestParam("longitude") Double longitude,
                            @RequestParam("latitude") Double latitude) throws DataLoadingException {
        meetingService.updateById(id, theme, meetingsDateTime, description, bookList, address, longitude, latitude);
    }

    @DeleteMapping("meeting/{id}")
    public void deleteMeeting(@PathVariable String id) throws DataLoadingException {
        meetingService.deleteById(id);
    }
}
