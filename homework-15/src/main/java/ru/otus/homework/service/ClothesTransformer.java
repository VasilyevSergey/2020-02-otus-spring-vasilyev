package ru.otus.homework.service;

import ru.otus.homework.domain.Clothes;

public interface ClothesTransformer {
    Clothes transformPantsToSorts(Clothes clothes);
}
