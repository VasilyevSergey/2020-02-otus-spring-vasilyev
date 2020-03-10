package ru.otus.homework.service;

import org.springframework.stereotype.Service;
import ru.otus.homework.domain.Person;

@Service
public class PersonServiceImpl implements PersonService {

    private final InputOutputServiceImpl ioService;
    private final LocalizationServiceImpl localizationService;

    public PersonServiceImpl(InputOutputServiceImpl ioService,
                             LocalizationServiceImpl localizationService) {
        this.ioService = ioService;
        this.localizationService = localizationService;
    }

    @Override
    public Person getPerson() {
        ioService.showMessage(localizationService.localizeMessage("test.firstName", null));
        String firstName = ioService.getMessage();

        ioService.showMessage(localizationService.localizeMessage("test.lastName", null));
        String lastName = ioService.getMessage();

        return new Person(firstName, lastName);
    }
}
