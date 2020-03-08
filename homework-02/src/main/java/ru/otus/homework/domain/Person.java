package ru.otus.homework.domain;

public class Person {
    private final String FirstName;
    private final String LastName;

    public Person(String firstName, String lastName) {
        FirstName = firstName;
        LastName = lastName;
    }

    public String getFirstName() {
        return FirstName;
    }

    public String getLastName() {
        return LastName;
    }
}
