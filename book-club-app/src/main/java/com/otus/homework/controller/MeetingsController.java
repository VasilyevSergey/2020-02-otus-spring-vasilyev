package com.otus.homework.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MeetingsController {

    @GetMapping("/bookClub/{id}/meetings/list/")
    public String meetingsPage() {
        return "meetings/list-by-book-club";
    }
}
