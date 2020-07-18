package com.otus.homework.dto;

import com.otus.homework.domain.Author;
import com.otus.homework.domain.Genre;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO автора
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {

    private String id;
    private String title;
    private Author author;
    private Genre genre;
}
