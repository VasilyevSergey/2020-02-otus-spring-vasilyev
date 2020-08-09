package ru.otus.homework.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.lang3.RandomUtils;
import ru.otus.homework.enums.ClothesType;
import ru.otus.homework.enums.Condition;

@Data
@AllArgsConstructor
public class Clothes {

    private ClothesType clothesType;

    private Condition condition;

    public static Clothes getRandomDirtyClothes() {
        ClothesType randomClothesType = ClothesType.values()[RandomUtils.nextInt(0, ClothesType.values().length)];
        return new Clothes(randomClothesType, Condition.DIRTY);
    }
}
