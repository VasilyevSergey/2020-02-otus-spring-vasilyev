package ru.otus.homework.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.homework.config.Settings;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Класс Settings")
class SettingsTest {

    @DisplayName("корректно создаётся конструктором")
    @Test
    void shouldHaveCorrectConstructor() {
        Settings settings = new Settings("ru-RU", "csv/QuestionsAndAnswers.csv");

        assertEquals("csv/QuestionsAndAnswers_ru_RU.csv", settings.getPathToLocalCSV());
        assertEquals(Locale.forLanguageTag("ru-RU"), settings.getLocale());
    }
}
