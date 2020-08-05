package com.otus.homework.repository;

import com.otus.homework.domain.Genre;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

@RepositoryRestResource(path = "genres")
public interface GenreRepository extends MongoRepository<Genre, String> {

    @RestResource(path = "names", rel = "names")
    List<Genre> findByName(String name);
}
