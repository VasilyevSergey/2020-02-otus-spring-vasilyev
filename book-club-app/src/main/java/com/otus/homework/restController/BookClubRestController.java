package com.otus.homework.restController;

import com.otus.homework.domain.BookClub;
import com.otus.homework.exception.DataLoadingException;
import com.otus.homework.service.BookClubService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookClubRestController {

    private final BookClubService bookClubService;

    public BookClubRestController(BookClubService bookClubService) {
        this.bookClubService = bookClubService;
    }

    @GetMapping("book-club")
    public List<BookClub> getAll() {
        return bookClubService.getAll();
    }

    @GetMapping("book-club/find-by-name")
    public List<BookClub> findAllByName(@RequestParam("name") String name) {
        return bookClubService.findAllByName(name);
    }

    @GetMapping("book-club/join/{id}")
    public BookClub joinToClub(@PathVariable("id") String id) throws DataLoadingException {
        return bookClubService.joinToClub(id);
    }

    @PostMapping("book-club")
    public void create(@RequestParam("name") String name,
                       @RequestParam("mainTheme") String mainTheme) throws DataLoadingException {
        bookClubService.create(name, mainTheme);
    }

    @GetMapping("book-club/{id}")
    public BookClub getById(@PathVariable String id) throws DataLoadingException {
        return bookClubService.getById(id);
    }

    @PutMapping("book-club/{id}")
    public void editById(@PathVariable("id") String id,
                         @RequestParam("name") String name,
                         @RequestParam("mainTheme") String mainTheme) throws DataLoadingException {
        bookClubService.updateById(id, name, mainTheme);
    }

    @DeleteMapping("book-club/{id}")
    public void delete(@PathVariable String id) throws DataLoadingException {
        bookClubService.deleteById(id);
    }
}
