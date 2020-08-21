package com.otus.homework.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BookClubController {

    @GetMapping("/")
    public String listPage() {
        return "bookClub/list";
    }
}
