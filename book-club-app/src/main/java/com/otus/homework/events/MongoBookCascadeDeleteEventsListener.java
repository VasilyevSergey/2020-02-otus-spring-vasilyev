package com.otus.homework.events;

import com.otus.homework.domain.Book;
import com.otus.homework.domain.Meeting;
import com.otus.homework.repository.BookRepository;
import com.otus.homework.repository.MeetingRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeDeleteEvent;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MongoBookCascadeDeleteEventsListener extends AbstractMongoEventListener<Book> {

    private final BookRepository bookRepository;
    private final MeetingRepository meetingRepository;

    // при удалении книги удаляем их из списков книг во встречах
    @Override
    public void onBeforeDelete(BeforeDeleteEvent<Book> event) {
        super.onBeforeDelete(event);
        val bookDocument = event.getSource();
        val id = bookDocument.get("_id").toString();
        Book book = bookRepository.findById(id).orElse(null);

        List<Meeting> meetingList = meetingRepository.findAllByBookListContaining(book);
        meetingList.forEach(meeting -> {
            List<Book> bookList = meeting.getBookList();
            bookList.remove(book);
            meeting.setBookList(bookList);
            meetingRepository.save(meeting);
        });

    }
}
