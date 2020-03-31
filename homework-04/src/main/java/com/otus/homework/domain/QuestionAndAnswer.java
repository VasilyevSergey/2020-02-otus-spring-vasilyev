package com.otus.homework.domain;

import lombok.Getter;

@Getter
public class QuestionAndAnswer {
    private final String question;
    private final String answer;

    public QuestionAndAnswer(String Question, String Answer) {
        this.question = Question;
        this.answer = Answer;
    }
}
