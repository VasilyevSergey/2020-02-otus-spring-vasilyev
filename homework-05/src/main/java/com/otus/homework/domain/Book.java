package com.otus.homework.domain;

import lombok.Data;

@Data
public class Book {
    private final Long id;
    private final String title;
    private final Author author;
    private final Genre genre;
}
