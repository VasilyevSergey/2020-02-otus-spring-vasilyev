package ru.otus.homework.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.homework.domain.Settings;

@Configuration
public class SettingConfig {

    @Value("${language.tag}")
    private String languageTag;

    @Value("${qna.filepath}")
    private String pathToCSV;

    @Bean
    public Settings settings() {
        return new Settings(languageTag, pathToCSV);
    }
}
