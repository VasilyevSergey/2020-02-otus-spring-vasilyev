package ru.otus.homework.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.dsl.channel.MessageChannels;
import org.springframework.integration.scheduling.PollerMetadata;
import ru.otus.homework.domain.Clothes;
import ru.otus.homework.enums.ClothesType;

@IntegrationComponentScan
@ComponentScan
@Configuration
@EnableIntegration
public class IntegrationConfig {

    @Bean
    public QueueChannel ordersChannel() {
        return MessageChannels.queue(10).get();
    }

    @Bean
    public PublishSubscribeChannel clothesChannel() {
        return MessageChannels.publishSubscribe().get();
    }

    @Bean(name = PollerMetadata.DEFAULT_POLLER)
    public PollerMetadata poller() {
        return Pollers.fixedRate(100).maxMessagesPerPoll(2).get();
    }

    @Bean
    public IntegrationFlow dryCleaningFlow() {
        return IntegrationFlows.from("ordersChannel")
                .split()
                .handle("dryCleaningServiceImpl", "cleanClothes")
                // все штаны превращаются в шорты
                .transform(Clothes.class, c -> {
                    if (c.getClothesType().equals(ClothesType.PANTS)) {
                        c.setClothesType(ClothesType.SHORTS);
                        System.out.println("Брюки превратились в штаны");
                    }
                    return c;
                })
                .aggregate()
                .channel("clothesChannel")
                .get();
    }
}
