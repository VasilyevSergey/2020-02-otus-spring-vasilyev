package ru.otus.homework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Scanner;

@Service
public class InputOutputServiceImpl implements InputOutputService {
    private Scanner scanner;

    public InputOutputServiceImpl() {
        this.scanner = new Scanner(System.in);
    }

    public void showMessage(String message) {
        System.out.println(message);
    }

    public String getMessage() {
        return scanner.next();
    }
}
