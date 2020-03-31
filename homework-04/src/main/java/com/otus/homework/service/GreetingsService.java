package com.otus.homework.service;

import com.otus.homework.domain.Person;

public interface GreetingsService {
    /**
     * Метод просит пользователя ввести свои имя и фамилию
     *
     * @return Персона
     */
    Person greeting();
}
