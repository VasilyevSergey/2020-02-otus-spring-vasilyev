package com.otus.homework.events;

import com.otus.homework.repository.AuthorRepository;
import com.otus.homework.repository.BookRepository;
import com.otus.homework.domain.Author;
import com.otus.homework.domain.Book;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeDeleteEvent;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MongoAuthorCascadeDeleteEventsListener extends AbstractMongoEventListener<Author> {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    @Override
    public void onBeforeDelete(BeforeDeleteEvent<Author> event) {
        super.onBeforeDelete(event);
        val authorDoc = event.getSource();
        val id = authorDoc.get("_id").toString();

        Author author = authorRepository.findById(id).orElse(null);
        List<Book> books = bookRepository.findAllByAuthor(author);
        books.forEach(book -> bookRepository.deleteById(book.getId()));
    }
}
