package com.otus.homework.dao;

import com.otus.homework.domain.AuthorSQL;
import org.springframework.data.repository.CrudRepository;

public interface AuthorRepository extends CrudRepository<AuthorSQL, String> {
}
