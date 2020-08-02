package com.otus.homework.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import com.otus.homework.domain.*;
import org.springframework.data.mongodb.core.MongoTemplate;


@ChangeLog
public class MongoDBChangelog {

    private Author pushkin;
    private Author tolkien;
    private Genre poem;
    private Genre fantasy;


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
        template.save(new Book("Ruslan and Lyudmila", pushkin, poem));
        template.save(new Book("Lord of the Rings", tolkien, fantasy));
    }
}
