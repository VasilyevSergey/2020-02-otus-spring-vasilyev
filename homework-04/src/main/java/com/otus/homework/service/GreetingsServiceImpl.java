package com.otus.homework.service;

import com.otus.homework.domain.Person;
import org.springframework.stereotype.Service;

@Service
public class GreetingsServiceImpl implements GreetingsService {
    private final PersonService personService;
    private final InputOutputService ioService;
    private final LocalizationService localizationService;

    public GreetingsServiceImpl(PersonService personService,
                                InputOutputService ioService,
                                LocalizationService localizationService) {
        this.personService = personService;
        this.ioService = ioService;
        this.localizationService = localizationService;
    }

    @Override
    public Person greeting() {
        ioService.showMessage(localizationService.localizeMessage("test.name"));
        return personService.getNewPerson();
    }
}
