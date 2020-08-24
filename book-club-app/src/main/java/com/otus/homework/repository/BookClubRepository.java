package com.otus.homework.repository;

import com.otus.homework.domain.BookClub;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface BookClubRepository extends MongoRepository<BookClub, String> {
    List<BookClub> findAllByNameContaining(String name);
}
