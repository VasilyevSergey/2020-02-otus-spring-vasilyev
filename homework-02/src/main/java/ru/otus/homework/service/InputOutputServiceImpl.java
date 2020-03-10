package ru.otus.homework.service;

import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

@Service
public class InputOutputServiceImpl implements InputOutputService {
    private static final InputStream IN_STREAM = System.in;
    private static final PrintStream OUT_STREAM = System.out;

    private final PrintStream outStream;
    private final Scanner scanner;

    public InputOutputServiceImpl() {
        this.outStream = OUT_STREAM;
        this.scanner = new Scanner(IN_STREAM);
    }

    public void showMessage(String message) {
        outStream.println(message);
    }

    public String getMessage() {
        return scanner.next();
    }
}

