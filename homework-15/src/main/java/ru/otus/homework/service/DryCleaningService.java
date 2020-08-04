package ru.otus.homework.service;

import ru.otus.homework.domain.Clothes;

public interface DryCleaningService {
    Clothes cleanClothes(Clothes clothes) throws InterruptedException;

    void startWork() throws InterruptedException;

}
