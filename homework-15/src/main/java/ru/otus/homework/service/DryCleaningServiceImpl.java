package ru.otus.homework.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Service;
import ru.otus.homework.config.DryCleaning;
import ru.otus.homework.domain.Clothes;
import ru.otus.homework.enums.Condition;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@SuppressWarnings("InfiniteLoopStatement")
@Service
public class DryCleaningServiceImpl implements DryCleaningService {

    private final DryCleaning dryCleaning;

    @Override
    public Clothes cleanClothes(Clothes clothes) throws InterruptedException {
        System.out.println("Cleaning " + clothes.getClothesType());
        clothes.setCondition(Condition.CLEAN);
        Thread.sleep(2000);
        System.out.println(clothes.getClothesType() + " cleaned");
        return clothes;
    }

    @Override
    public void startWork() throws InterruptedException {
        while (true) {
            Thread.sleep(1000);

            Collection<Clothes> order = generateOrder();
            System.out.println("New order: " + getOrderAsString(order));

            Collection<Clothes> completedOrder = dryCleaning.fulfill(order);
            System.out.println("Order completed: " + getOrderAsString(completedOrder));
        }
    }

    private Collection<Clothes> generateOrder() {
        List<Clothes> order = new ArrayList<>();
        for (int i = 0; i < RandomUtils.nextInt(1, 5); ++i) {
            order.add(Clothes.getRandomDirtyClothes());
        }
        return order;
    }

    private String getOrderAsString(Collection<Clothes> order) {
        return order.stream().map(clothes -> String.format("%s %s",
                clothes.getCondition().name(),
                clothes.getClothesType().name()))
                .collect(Collectors.joining(", "));
    }
}
