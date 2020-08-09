package ru.otus.homework.config;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import ru.otus.homework.domain.Clothes;

import java.util.Collection;

@MessagingGateway
public interface DryCleaning {

    @Gateway(requestChannel = "ordersChannel", replyChannel = "clothesChannel")
    Collection<Clothes> fulfill(Collection<Clothes> order);
}
