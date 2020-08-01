package com.otus.homework.repository;

import com.otus.homework.domain.UserDB;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<UserDB, String> {
    UserDB findByUsername(String username);
}
