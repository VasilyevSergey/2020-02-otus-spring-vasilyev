package com.otus.homework.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
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

    private String bookId;

    private LocalDateTime datetime;

    private String commentator;

    public Comment(String comment, String bookId, LocalDateTime datetime, String commentator) {
        this.comment = comment;
        this.bookId = bookId;
        this.datetime = datetime;
        this.commentator = commentator;
    }
}
