package com.otus.homework.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import com.otus.homework.domain.*;
import lombok.val;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;

@ChangeLog
public class DatabaseChangelog {

    private Author pushkin;
    private Author tolkien;
    private Genre poem;
    private Genre fantasy;

    private Book ruslanAndLudmila;
    private Book lordOfTheRings;

    @ChangeSet(order = "000", id = "dropDB", author = "vasilyev", runAlways = true)
    public void dropDB(MongoDatabase database) {
        database.drop();
    }

    @ChangeSet(order = "001", id = "addAuthors", author = "vasilyev", runAlways = true)
    public void addAuthors(MongoTemplate template) {
        pushkin = template.save(new Author("Pushkin"));
        tolkien = template.save(new Author("Tolkien"));
    }

    @ChangeSet(order = "002", id = "addGenres", author = "vasilyev")
    public void addGenres(MongoTemplate template) {
        poem = template.save(new Genre("A poem in verse"));
        fantasy = template.save(new Genre("Fantasy"));
    }

    @ChangeSet(order = "003", id = "addBooks", author = "vasilyev")
    public void addBooks(MongoTemplate template) {
        val firstBook = new Book("Ruslan and Lyudmila", pushkin, poem);
        val secondBook = new Book("Lord of the Rings", tolkien, fantasy);

        ruslanAndLudmila = template.save(firstBook);
        lordOfTheRings = template.save(secondBook);
    }

    @ChangeSet(order = "004", id = "addComments", author = "vasilyev")
    public void addComments(MongoTemplate template) {
        Comment firstComment = new Comment(
                "Cool",
                ruslanAndLudmila,
                LocalDateTime.of(2020, 1, 1, 1, 1),
                "Vasya");
        Comment secondComment = new Comment(
                "Nice",
                ruslanAndLudmila,
                LocalDateTime.of(2020, 1, 1, 1, 2),
                "Marusya");
        Comment thirdComment = new Comment(
                "Brilliant",
                lordOfTheRings,
                LocalDateTime.of(2020, 1, 1, 1, 3),
                "Petya");

        template.save(firstComment);
        template.save(secondComment);
        template.save(thirdComment);
    }

    @ChangeSet(order = "005", id = "addUsers", author = "vasilyev", runAlways = true)
    public void addUsers(MongoTemplate template) {
        template.save(new UserDB("1", "user", new BCryptPasswordEncoder().encode("user")));
        template.save(new UserDB("2", "admin", new BCryptPasswordEncoder().encode("admin")));
    }
}
