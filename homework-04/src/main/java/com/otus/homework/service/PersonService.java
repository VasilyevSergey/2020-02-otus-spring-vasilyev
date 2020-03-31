package com.otus.homework.service;

import com.otus.homework.domain.Person;

public interface PersonService {
    /**
     * Метод для получения новой персоны
     *
     * @return Новая персона
     */
    Person getNewPerson();

    /**
     * Метод для получения сохраненой персоны
     *
     * @return Сохраненная персона
     */
    Person getPerson();
}
