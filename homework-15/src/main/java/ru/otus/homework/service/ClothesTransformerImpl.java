package ru.otus.homework.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.homework.domain.Clothes;
import ru.otus.homework.enums.ClothesType;

@RequiredArgsConstructor
@Service
public class ClothesTransformerImpl implements ClothesTransformer {

    @Override
    public Clothes transformPantsToSorts(Clothes clothes) {
        if (clothes.getClothesType().equals(ClothesType.PANTS)) {
            clothes.setClothesType(ClothesType.SHORTS);
            System.out.println("Брюки превратились в шорты");
        }
        return clothes;
    }
}
