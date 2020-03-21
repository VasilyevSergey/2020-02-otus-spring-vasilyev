package com.otus.homework.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(Settings.class)
@EnableConfigurationProperties(Props.class)
public class SettingsConfig {
    private final Props props;

    public SettingsConfig(Props props) {
        this.props = props;
    }

    @Bean
    @ConditionalOnMissingBean
    public Settings settings() {
        return new Settings(props);
    }
}
