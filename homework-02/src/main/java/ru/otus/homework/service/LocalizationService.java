package ru.otus.homework.service;

public interface LocalizationService {

    /**
     * Локализация строки
     *
     * @param bundleCode код сообщения, например, 'test.result'.
     * @param args       аргументы, которые подставляются в строку
     * @return Локализованная строка
     */
    String localizeMessage(String bundleCode, Object... args);
}
