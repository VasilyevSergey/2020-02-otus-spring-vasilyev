package ru.otus.homework.service;

import org.springframework.context.MessageSource;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import ru.otus.homework.domain.Settings;

import java.util.Locale;

@Service
public class LocalizationServiceImpl implements LocalizationService {

    private final MessageSource messageSource;
    private final Settings settings;

    public LocalizationServiceImpl(MessageSource messageSource,
                                   Settings settings) {
        this.messageSource = messageSource;
        this.settings = settings;
    }

    public String localizeMessage(String bundleCode, @Nullable Object... args) {
        return messageSource.getMessage(bundleCode, args, Locale.forLanguageTag(settings.getLanguageTag()));
    }
}
