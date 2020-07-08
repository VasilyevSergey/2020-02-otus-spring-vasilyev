package com.otus.homework.testChangelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import com.otus.homework.domain.Author;
import com.otus.homework.domain.Book;
import com.otus.homework.domain.Comment;
import com.otus.homework.domain.Genre;
import lombok.val;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.time.LocalDateTime;

@ChangeLog
public class TestChangelog {

    private Author pushkin;
    private Author tolkien;
    private Genre poem;
    private Genre fantasy;

    private Book ruslanAndLudmila;
    private Book lordOfTheRings;

    @ChangeSet(order = "000", id = "dropDB", author = "vasilyev")
    public void dropDB(MongoDatabase database) {
        database.drop();
    }


    @ChangeSet(order = "001", id = "addAuthors", author = "vasilyev")
    public void addAuthors(MongoTemplate template) {
        pushkin = template.save(new Author("1", "Pushkin"));
        tolkien = template.save(new Author("2", "Tolkien"));
    }

    @ChangeSet(order = "002", id = "addGenres", author = "vasilyev")
    public void addGenres(MongoTemplate template) {
        poem = template.save(new Genre("1", "A poem in verse"));
        fantasy = template.save(new Genre("2", "Fantasy"));
    }

    @ChangeSet(order = "003", id = "addBooks", author = "vasilyev")
    public void addBooks(MongoTemplate template) {
        val firstBook = new Book("1", "Ruslan and Lyudmila", pushkin.getId(), poem.getId());
        val secondBook = new Book("2", "Lord of the Rings", tolkien.getId(), fantasy.getId());

        ruslanAndLudmila = template.save(firstBook);
        lordOfTheRings = template.save(secondBook);
    }

    @ChangeSet(order = "004", id = "addComments", author = "vasilyev")
    public void addComments(MongoTemplate template) {
        Comment firstComment = new Comment(
                "1",
                "Cool",
                ruslanAndLudmila.getId(),
                LocalDateTime.of(2020, 1, 1, 1, 1),
                "Vasya");
        Comment secondComment = new Comment(
                "2",
                "Nice",
                ruslanAndLudmila.getId(),
                LocalDateTime.of(2020, 1, 1, 1, 2),
                "Marusya");
        Comment thirdComment = new Comment(
                "3",
                "Brilliant",
                lordOfTheRings.getId(),
                LocalDateTime.of(2020, 1, 1, 1, 3),
                "Petya");

        template.save(firstComment);
        template.save(secondComment);
        template.save(thirdComment);
    }
}
