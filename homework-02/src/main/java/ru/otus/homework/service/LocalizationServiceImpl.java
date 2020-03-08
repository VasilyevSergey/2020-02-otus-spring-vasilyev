package ru.otus.homework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class LocalizationServiceImpl implements LocalizationService {
    @Autowired
    private MessageSource messageSource;

    public String localizeMessage(String bundleCode) {
        //return messageSource.getMessage("test.name", null, null);
        return messageSource.getMessage("test.name", null, Locale.US);
        //return messageSource.getMessage("test.name", null, Locale.forLanguageTag("en-US"));
        //return messageSource.getMessage("test.name", null, Locale.forLanguageTag("ru-RU"));
    }
}
