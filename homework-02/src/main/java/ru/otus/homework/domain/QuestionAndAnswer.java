package ru.otus.homework.domain;

public class QuestionAndAnswer {
    private final String Question;
    private final String Answer;

    public QuestionAndAnswer(String Question, String Answer) {
        this.Question = Question;
        this.Answer = Answer;
    }

    public String getQuestion() {
        return Question;
    }

    public String getAnswer() {
        return Answer;
    }
}
