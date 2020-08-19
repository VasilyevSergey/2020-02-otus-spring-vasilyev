package com.otus.homework.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class AuthorController {
    @GetMapping("/")
    public String listPage(Model model) {
        String newAuthorName = "";
        model.addAttribute("newAuthorName", newAuthorName);
        return "author/list";
    }

    @GetMapping("/author/edit/")
    public String editPage() {
        return "author/edit";
    }
}
