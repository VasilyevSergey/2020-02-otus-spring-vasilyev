package ru.otus.homework.domain;

public class QuestionAndAnswer {
    private final String question;
    private final String answer;

    public QuestionAndAnswer(String Question, String Answer) {
        this.question = Question;
        this.answer = Answer;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }
}
