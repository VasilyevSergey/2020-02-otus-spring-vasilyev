package com.otus.homework.events;

import com.otus.homework.domain.BookClub;
import com.otus.homework.domain.Meeting;
import com.otus.homework.repository.BookClubRepository;
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
public class MongoBookClubCascadeDeleteEventsListener extends AbstractMongoEventListener<BookClub> {

    private final BookClubRepository bookClubRepository;
    private final BookRepository bookRepository;
    private final MeetingRepository meetingRepository;

    // при удалении клуба удаляем встречи
    @Override
    public void onBeforeDelete(BeforeDeleteEvent<BookClub> event) {
        super.onBeforeDelete(event);
        val bookClubDocument = event.getSource();
        val id = bookClubDocument.get("_id").toString();
        BookClub bookClub = bookClubRepository.findById(id).orElse(null);

        List<Meeting> meetingList = meetingRepository.findAllByBookClub(bookClub);
        meetingList.forEach(meetingRepository::delete);
    }
}
