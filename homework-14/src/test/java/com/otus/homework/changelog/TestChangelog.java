package com.otus.homework.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import com.otus.homework.domain.Author;
import com.otus.homework.domain.Book;
import com.otus.homework.domain.Genre;
import lombok.val;
import org.springframework.data.mongodb.core.MongoTemplate;

@ChangeLog
public class TestChangelog {

    private Author pushkin;
    private Author tolkien;
    private Genre poem;
    private Genre fantasy;

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
        val firstBook = new Book("1", "Ruslan and Lyudmila", pushkin, poem);
        val secondBook = new Book("2", "Lord of the Rings", tolkien, fantasy);

        template.save(firstBook);
        template.save(secondBook);
    }
}
