package com.otus.homework.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "comment", nullable = false)
    private String comment;

    @ManyToOne(targetEntity = Book.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "book_id")
    private Book book;

    @Column(name = "datetime", nullable = false)
    private ZonedDateTime datetime;

    @Column(name = "commentator", nullable = false)
    private String commentator;

    public Comment(String comment, Book book, ZonedDateTime datetime, String commentator) {
        this.comment = comment;
        this.book = book;
        this.datetime = datetime;
        this.commentator = commentator;
    }
}
