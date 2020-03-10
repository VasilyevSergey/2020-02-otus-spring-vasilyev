package ru.otus.homework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class LocalizationServiceImpl implements LocalizationService {

    @Autowired
    private MessageSource messageSource;

    private final Locale locale;

    public LocalizationServiceImpl(@Value("${language.tag}") String languageTag) {
        this.locale = Locale.forLanguageTag(languageTag);
    }

    public String localizeMessage(String bundleCode, @Nullable Object[] var2) {
        return messageSource.getMessage(bundleCode, var2, locale);
    }
}
