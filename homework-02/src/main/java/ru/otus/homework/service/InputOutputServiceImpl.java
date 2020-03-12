package ru.otus.homework.service;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class InputOutputServiceImpl implements InputOutputService {

    private final PrintStream outStream;
    private final Scanner scanner;

    public InputOutputServiceImpl(InputStream inputStream,
                                  PrintStream outStream) {
        this.outStream = outStream;
        this.scanner = new Scanner(inputStream);
    }

    public void showMessage(String message) {
        outStream.println(message);
    }

    public String getMessage() {
        return scanner.next();
    }
}

