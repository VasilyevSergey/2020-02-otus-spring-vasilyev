package com.otus.homework.config;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Locale;

@DisplayName("Класс Settings")
@SpringBootTest
class SettingsTest {

    @Autowired
    private Settings settings;

    @DisplayName(" возвращает корректную локаль")
    @Test
    void shouldReturnCorrectLocale() {
        Locale expectedLocale = Locale.forLanguageTag("ru-RU");
        assertEquals(expectedLocale, settings.getLocale());
    }

    @DisplayName(" возвращает корректный путь до файла с локализованными вопросами")
    @Test
    void shouldReturnCorrectPathToLocalCSV() {
        String expectedPathToLocalCSV = "csv/QuestionsAndAnswers_ru_RU.csv";
        assertEquals(expectedPathToLocalCSV, settings.getPathToLocalCSV());
    }
}
