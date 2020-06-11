package com.otus.homework.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "comments")
public class Comment {
    @Id
    private String id;

    private String comment;

    @DBRef
    private Book book;

    private LocalDateTime datetime;

    private String commentator;

    public Comment(String comment, Book book, LocalDateTime datetime, String commentator) {
        this.comment = comment;
        this.book = book;
        this.datetime = datetime;
        this.commentator = commentator;
    }
}
