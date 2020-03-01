package ru.otus.homework.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Класс QuestionAndAnswer")
class QuestionAndAnswerTest {

    @DisplayName("корректно создаётся конструктором")
    @Test
    void shouldHaveCorrectConstructor() {
        QuestionAndAnswer qna = new QuestionAndAnswer("Question?", "Answer");

        assertEquals("Question?", qna.getQuestion());
        assertEquals("Answer", qna.getAnswer());
    }
}
