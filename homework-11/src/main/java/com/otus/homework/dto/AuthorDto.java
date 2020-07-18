package com.otus.homework.dto;

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
}
