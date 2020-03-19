package ru.otus.homework.service;

import org.springframework.stereotype.Service;
import ru.otus.homework.domain.Person;

@Service
public class PersonServiceImpl implements PersonService {

    private final InputOutputService ioService;
    private final LocalizationService localizationService;

    public PersonServiceImpl(InputOutputService ioService,
                             LocalizationService localizationService) {
        this.ioService = ioService;
        this.localizationService = localizationService;
    }

    @Override
    public Person getPerson() {
        ioService.showMessage(localizationService.localizeMessage("test.firstName"));
        String firstName = ioService.getMessage();

        ioService.showMessage(localizationService.localizeMessage("test.lastName"));
        String lastName = ioService.getMessage();

        return new Person(firstName, lastName);
    }
}
