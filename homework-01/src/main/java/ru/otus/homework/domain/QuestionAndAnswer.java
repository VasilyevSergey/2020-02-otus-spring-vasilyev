package ru.otus.homework.domain;

public class QuestionAndAnswer {
    private final String Question;
    private final String Answer;

    public QuestionAndAnswer(String question, String answer) {
        Question = question;
        Answer = answer;
    }

    public String getQuestion() {
        return Question;
    }

    public String getAnswer() {
        return Answer;
    }
}
