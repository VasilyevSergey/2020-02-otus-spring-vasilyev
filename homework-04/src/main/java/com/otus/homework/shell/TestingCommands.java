package com.otus.homework.shell;

import com.otus.homework.domain.Person;
import com.otus.homework.service.GreetingsService;
import com.otus.homework.service.LocalizationService;
import com.otus.homework.service.QuestionAndAnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

@ShellComponent
@RequiredArgsConstructor
public class TestingCommands {
    private final GreetingsService greetingsService;
    private final QuestionAndAnswerService qnaService;
    private final LocalizationService localizationService;

    private Person person;

    @ShellMethod(value = "Login command", key = {"l", "login"})
    public String login() {
        this.person = greetingsService.greeting();
        return String.format("Добро пожаловать: %s %s", person.getFirstName(), person.getLastName());
    }

    @ShellMethod(value = "Start test", key = {"s", "start"})
    @ShellMethodAvailability(value = "isStartTestCommandAvailable")
    public void startTest() {
        qnaService.startTest();
    }

    private Availability isStartTestCommandAvailable() {
        return person == null
                ? Availability.unavailable(localizationService.localizeMessage("test.notLoggedIn"))
                : Availability.available();
    }
}
