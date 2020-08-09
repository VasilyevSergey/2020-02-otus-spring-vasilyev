package ru.otus.homework.config;

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
import ru.otus.homework.service.DryCleaningService;
import ru.otus.homework.service.ClothesTransformer;

@IntegrationComponentScan
@ComponentScan
@Configuration
@EnableIntegration
public class IntegrationConfig {

    private static final String TRANSFORM_PANTS_TO_SORTS = "transformPantsToSorts";
    private static final String CLEAN_CLOTHES = "cleanClothes";
    private final DryCleaningService dryCleaningService;
    private final ClothesTransformer clothesTransformer;

    public IntegrationConfig(DryCleaningService dryCleaningService,
                             ClothesTransformer clothesTransformer) {
        this.dryCleaningService = dryCleaningService;
        this.clothesTransformer = clothesTransformer;
    }

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
                .handle(dryCleaningService, CLEAN_CLOTHES)
                // все штаны превращаются в шорты
                .transform(clothesTransformer, TRANSFORM_PANTS_TO_SORTS)
                .aggregate()
                .channel("clothesChannel")
                .get();
    }
}
