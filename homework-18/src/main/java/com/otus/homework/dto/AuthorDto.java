package com.otus.homework.dto;

import com.otus.homework.domain.Author;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO автора
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorDto {

    private String id;
    private String name;

    public AuthorDto(String name) {
        this.name = name;
    }

    public static AuthorDto toDto(Author author) {
        return new AuthorDto(author.getId(), author.getName());
    }
}
