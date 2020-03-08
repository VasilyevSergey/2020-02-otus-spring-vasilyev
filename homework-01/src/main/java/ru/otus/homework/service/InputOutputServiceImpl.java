package ru.otus.homework.service;

import java.util.Scanner;

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
