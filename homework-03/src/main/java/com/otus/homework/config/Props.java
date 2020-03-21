package com.otus.homework.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix="application")
public class Props {
    private String pathToCSV;
    private String languageTag;
}
