package ru.otus.homework.service;

import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LocalizationServiceImplTest {

    @Test
    void shouldHaveCorrectConstructor() {
        LocalizationServiceImpl localizationService = new LocalizationServiceImpl("ru-RU");
        assertEquals(Locale.forLanguageTag("ru-RU"), localizationService.getLocale());
    }
}
