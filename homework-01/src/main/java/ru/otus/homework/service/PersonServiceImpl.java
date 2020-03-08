package ru.otus.homework.service;

import ru.otus.homework.domain.Person;

public class PersonServiceImpl implements PersonService {

    private final InputOutputServiceImpl ioService;

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
