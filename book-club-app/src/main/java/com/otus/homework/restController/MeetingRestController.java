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
    public List<Meeting> getAll() {
        return meetingService.getAll();
    }

    @PostMapping("meeting")
    public void create(@RequestParam("theme") String theme,
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
    public Meeting get(@PathVariable String id) throws DataLoadingException {
        return meetingService.getById(id);
    }

    @PutMapping("meeting/{id}")
    public void edit(@PathVariable("id") String id,
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
    public void delete(@PathVariable String id) throws DataLoadingException {
        meetingService.deleteById(id);
    }

    @GetMapping("meeting/find-all-by-book-club")
    public List<Meeting> findAllByBookClub(@RequestParam("bookClub") BookClub bookClub) {
        return meetingService.findAllByBookClub(bookClub);
    }

    @GetMapping("meeting/find-all-by-theme")
    public List<Meeting> findAllByTheme(@RequestParam("theme") String theme) {
        return meetingService.findAllByTheme(theme);
    }

    @GetMapping("meeting/join/{id}")
    public Meeting joinToClub(@PathVariable("id") String id) throws DataLoadingException {
        return meetingService.joinToMeeting(id);
    }
}
