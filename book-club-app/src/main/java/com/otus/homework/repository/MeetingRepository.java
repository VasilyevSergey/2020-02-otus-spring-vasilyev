package com.otus.homework.repository;

import com.otus.homework.domain.Book;
import com.otus.homework.domain.Meeting;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MeetingRepository extends MongoRepository<Meeting, String> {
}
