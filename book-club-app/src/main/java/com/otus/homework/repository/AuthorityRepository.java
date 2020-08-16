package com.otus.homework.repository;

import com.otus.homework.domain.Authority;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AuthorityRepository extends MongoRepository<Authority, String> {}
