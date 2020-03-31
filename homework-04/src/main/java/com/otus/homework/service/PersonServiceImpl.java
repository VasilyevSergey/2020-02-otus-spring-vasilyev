package com.otus.homework.service;

import org.springframework.stereotype.Service;
import com.otus.homework.domain.Person;

@Service
public class PersonServiceImpl implements PersonService {

    private final InputOutputService ioService;
    private final LocalizationService localizationService;

    private Person person;

    public PersonServiceImpl(InputOutputService ioService,
                             LocalizationService localizationService) {
        this.ioService = ioService;
        this.localizationService = localizationService;
    }

    @Override
    public Person getNewPerson() {
        ioService.showMessage(localizationService.localizeMessage("test.firstName"));
        String firstName = ioService.getMessage();

        ioService.showMessage(localizationService.localizeMessage("test.lastName"));
        String lastName = ioService.getMessage();
        person = new Person(firstName, lastName);

        return person;
    }

    public Person getPerson() {
        return person;
    }
}
