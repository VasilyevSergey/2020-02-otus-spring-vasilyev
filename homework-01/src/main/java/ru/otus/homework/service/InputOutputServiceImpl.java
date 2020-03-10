package ru.otus.homework.service;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class InputOutputServiceImpl implements InputOutputService {
    private PrintStream outStream;
    private Scanner scanner;

    public InputOutputServiceImpl(InputStream inStream,
                                  PrintStream outStream) {
        this.outStream = outStream;
        this.scanner = new Scanner(inStream);
    }

    public void showMessage(String message) {
        outStream.println(message);
    }

    public String getMessage() {
        return scanner.next();
    }
}
