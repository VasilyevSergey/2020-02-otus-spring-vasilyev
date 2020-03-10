package ru.otus.homework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.homework.domain.Person;

@Service
public class PersonServiceImpl implements PersonService {

    private InputOutputServiceImpl ioService;

    @Autowired
    public PersonServiceImpl(InputOutputServiceImpl ioService) {
        this.ioService = ioService;
    }

    @Override
    public Person getPerson() {
        ioService.showMessage("Enter your first name");
        String firstName = ioService.getMessage();

        ioService.showMessage("Enter your last name");
        String lastName = ioService.getMessage();

        return new Person(firstName, lastName);
    }
}
