package com.otus.homework.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import com.otus.homework.domain.*;
import lombok.val;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static com.otus.homework.security.AuthoritiesConstants.ADMIN;
import static com.otus.homework.security.AuthoritiesConstants.USER;

@ChangeLog
public class TestChangelog {

    private Author pushkin;
    private Author tolkien;

    private Book ruslanAndLudmila;
    private Book lordOfTheRings;

    private User firstUser;
    private User secondUser;
    private User thirdUser;
    private User admin;

    BookClub PuskinFanClub;
    BookClub LOTR;

    @ChangeSet(order = "000", id = "dropDB", author = "vasilyev", runAlways = true)
    public void dropDB(MongoDatabase database) {
        database.drop();
    }

    @ChangeSet(order = "001", id = "addUsers", author = "vasilyev", runAlways = true)
    public void addUsers(MongoTemplate template) {

        admin = new User(
                "1",
                "admin",
                new BCryptPasswordEncoder().encode("admin"),
                "firstName",
                "lastName",
                Set.of(new Authority(ADMIN))
        );

        firstUser = new User(
                "2",
                "firstUser",
                new BCryptPasswordEncoder().encode("firstUser"),
                "Aleksandr",
                "Ivanov",
                Set.of(new Authority(USER))
        );

        secondUser = new User(
                "3",
                "secondUser",
                new BCryptPasswordEncoder().encode("secondUser"),
                "Tatiana",
                "Sidorova",
                Set.of(new Authority(USER))
        );

        thirdUser = new User(
                "3",
                "thirdUser",
                new BCryptPasswordEncoder().encode("secondUser"),
                "Feanor",
                "Nodlor",
                Set.of(new Authority(USER))
        );

        template.save(firstUser);
        template.save(secondUser);
        template.save(thirdUser);
        template.save(admin);
    }

    @ChangeSet(order = "002", id = "addAuthors", author = "vasilyev", runAlways = true)
    public void addAuthors(MongoTemplate template) {
        pushkin = template.save(new Author("1", "Pushkin"));
        tolkien = template.save(new Author("2", "Tolkien"));
    }

    @ChangeSet(order = "003", id = "addBooks", author = "vasilyev")
    public void addBooks(MongoTemplate template) {
        val firstBook = new Book("Ruslan and Lyudmila", pushkin);
        val secondBook = new Book("Lord of the Rings", tolkien);

        ruslanAndLudmila = template.save(firstBook);
        lordOfTheRings = template.save(secondBook);
    }

    @ChangeSet(order = "004", id = "addBookClubs", author = "vasilyev")
    public void addBookClubs(MongoTemplate template) {
        BookClub firstClub = new BookClub();

        LOTR = new BookClub(
                "LOTR book club",
                firstUser,
                "Fan club of Lord of the rings",
                List.of(firstUser, secondUser));

        PuskinFanClub = new BookClub(
                "PuskinFanClub",
                secondUser,
                "Fan club of Pushkin",
                List.of(secondUser, thirdUser));

        template.save(LOTR);
        template.save(PuskinFanClub);
    }

    @ChangeSet(order = "005", id = "addMeetings", author = "vasilyev")
    public void addMeetings(MongoTemplate template) {
        Meeting LOTRMeeting = new Meeting(
                "1",
                LocalDateTime.of(
                        2020,
                        9,
                        6,
                        15,
                        0,
                        0,
                        0),
                "Monthly meeting of the club",
                List.of(lordOfTheRings),
                LOTR,
                1.0,
                1.0,
                firstUser,
                List.of(firstUser, secondUser));

        template.save(LOTRMeeting);
    }
}
