package com.otus.homework.restController;

import com.otus.homework.domain.Author;
import com.otus.homework.domain.BookClub;
import com.otus.homework.exception.DataLoadingException;
import com.otus.homework.service.AuthorService;
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
    public List<BookClub> getAllBookClubs() {
        return bookClubService.getAll();
    }

    @PostMapping("book-club")
    public void createBookClub(@RequestParam("name") String name,
                               @RequestParam("mainTheme") String mainTheme) throws DataLoadingException {
        bookClubService.create(name, mainTheme);
    }

    @GetMapping("book-club/{id}")
    public BookClub getBookClub(@PathVariable String id) throws DataLoadingException {
        return bookClubService.getById(id);
    }

    @PutMapping("book-club/{id}")
    public void editBookClubInfo(@PathVariable("id") String id,
                                 @RequestParam("name") String name,
                                 @RequestParam("mainTheme") String mainTheme) throws DataLoadingException {
        bookClubService.updateById(id, name, mainTheme);
    }

    @DeleteMapping("book-club/{id}")
    public void deleteBookClub(@PathVariable String id) throws DataLoadingException {
        bookClubService.deleteById(id);
    }
}
