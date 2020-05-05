package com.otus.homework;

import com.otus.homework.dao.AuthorRepository;
import com.otus.homework.domain.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories
@EnableConfigurationProperties
@SpringBootApplication
public class Main {

//    @SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
//    @Autowired
//    private AuthorRepository repository;

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(Main.class, args);

//        ApplicationContext context = SpringApplication.run(Main.class);
//
//        AuthorRepository repository = context.getBean(AuthorRepository.class);
//
//        repository.save(new Author("Pushkin"));
//
//        Thread.sleep(3000);
//
//        repository.findAll().forEach(p -> System.out.println(p.getName()));
    }
}
