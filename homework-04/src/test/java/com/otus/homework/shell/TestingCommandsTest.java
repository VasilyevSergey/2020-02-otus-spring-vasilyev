package com.otus.homework.shell;

import com.otus.homework.domain.Person;
import com.otus.homework.service.GreetingsService;
import com.otus.homework.service.LocalizationService;
import com.otus.homework.service.QuestionAndAnswerService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.shell.CommandNotCurrentlyAvailable;
import org.springframework.shell.Shell;
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@DisplayName("Тест команд shell ")
@SpringBootTest
public class TestingCommandsTest {

    @MockBean
    private GreetingsService greetingsService;

    @MockBean
    private QuestionAndAnswerService qnaService;

    @MockBean
    private LocalizationService localizationService;

    @Autowired
    private Shell shell;

    private static final String YOU_MUST_LOGIN_FIRST = "You must login first";
    private static final String COMMAND_LOGIN = "login";
    private static final String COMMAND_LOGIN_SHORT = "l";
    private static final String COMMAND_START = "start";
    private static final String COMMAND_START_SHORT = "s";
    private static final String DEFAULT_PERSON_FIRST_NAME = "Vasya";
    private static final String DEFAULT_PERSON_LAST_NAME = "Pupkin";
    private static final Person DEFAULT_PERSON = new Person(DEFAULT_PERSON_FIRST_NAME, DEFAULT_PERSON_LAST_NAME);
    private static final String GREETING_PATTERN = "Добро пожаловать: %s %s";

    @DisplayName(" должен возвращать приветствие и вызвать соответствующий метод сервиса для команды логина")
    @Test
    void shouldReturnExpectedGreetingFirePublishMethodAfterLoginCommandEvaluated() {
        given(greetingsService.greeting()).willReturn(DEFAULT_PERSON);

        String res = (String) shell.evaluate(() -> COMMAND_LOGIN);
        assertThat(res).isEqualTo(String.format(GREETING_PATTERN, DEFAULT_PERSON_FIRST_NAME, DEFAULT_PERSON_LAST_NAME));
        verify(greetingsService, times(1)).greeting();

        res = (String) shell.evaluate(() -> COMMAND_LOGIN_SHORT);
        assertThat(res).isEqualTo(String.format(GREETING_PATTERN, DEFAULT_PERSON_FIRST_NAME, DEFAULT_PERSON_LAST_NAME));
        verify(greetingsService, times(2)).greeting();
    }

    @DisplayName(" должен возвращать CommandNotCurrentlyAvailable если при попытке выполнения команды start перед командой login")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    @Test
    void shouldReturnCommandNotCurrentlyAvailableObjectWhenUserDoesNotLoginAfterStartEvaluated() {
        given(localizationService.localizeMessage(any()))
                .willReturn(YOU_MUST_LOGIN_FIRST);

        Object res =  shell.evaluate(() -> COMMAND_START);
        assertThat(res).isInstanceOf(CommandNotCurrentlyAvailable.class);

        res =  shell.evaluate(() -> COMMAND_START_SHORT);
        assertThat(res).isInstanceOf(CommandNotCurrentlyAvailable.class);
    }

    @DisplayName(" должен вызвать соответствующий метод сервиса, если выполнена команда start")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    @Test
    void shouldFirePublishMethodAfterStartCommandEvaluated() {
        given(greetingsService.greeting()).willReturn(DEFAULT_PERSON);
        shell.evaluate(() -> COMMAND_LOGIN);
        shell.evaluate(() -> COMMAND_START);
        verify(qnaService, times(1)).startTest();
    }
}
