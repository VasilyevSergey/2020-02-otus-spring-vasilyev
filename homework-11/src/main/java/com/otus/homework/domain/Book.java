package com.otus.homework.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "books")
public class Book {
    @Id
    private String id;

    private String title;

    private String authorId;

    private String genreId;

    public Book(String title, String authorId, String genreId) {
        this.title = title;
        this.authorId = authorId;
        this.genreId = genreId;
    }
}
