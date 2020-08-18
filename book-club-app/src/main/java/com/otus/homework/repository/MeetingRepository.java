package com.otus.homework.repository;

import com.otus.homework.domain.BookClub;
import com.otus.homework.domain.Meeting;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MeetingRepository extends MongoRepository<Meeting, String> {

    List<Meeting> findAllByBookClub(BookClub bookClub);

    List<Meeting> findAllByTheme(String theme);
}
