package ru.otus.homework.dao;

import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.homework.domain.QuestionAndAnswer;
import ru.otus.homework.config.Settings;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

@DisplayName("Класс QuestionAndAnswerDAOImpl ")
@ExtendWith(MockitoExtension.class)
public class QuestionAndAnswerDAOImplTest {

    @Mock
    private Settings settings;

    private QuestionAndAnswerDAOImpl dao;

    @BeforeEach
    void setUp() {
        given(settings.getPathToLocalCSV())
                .willReturn("csv/QuestionsAndAnswers_ru_RU.csv");

        dao = new QuestionAndAnswerDAOImpl(settings);
    }

    @DisplayName("корректно создаётся конструктором")
    @Test
    void shouldHaveCorrectConstructor() {
        assertEquals("csv/QuestionsAndAnswers_ru_RU.csv", dao.getPathToLocalCSV());
    }

    @SneakyThrows
    @DisplayName("возвращает список локализованных вопросов и ответов")
    @Test
    void questionAndAnswerListTest() {
        List<QuestionAndAnswer> questionAndAnswerList = dao.questionAndAnswerList();

        List<QuestionAndAnswer> expectedList = Arrays.asList(
                new QuestionAndAnswer("1. Вопрос 1?", "Ответ 1"),
                new QuestionAndAnswer("2. Вопрос 2?", "Ответ 2"));

        assertEquals(expectedList.size(), questionAndAnswerList.size());
        assertEquals(expectedList.get(0).getAnswer(), questionAndAnswerList.get(0).getAnswer());
        assertEquals(expectedList.get(0).getQuestion(), questionAndAnswerList.get(0).getQuestion());
        assertEquals(expectedList.get(1).getAnswer(), questionAndAnswerList.get(1).getAnswer());
        assertEquals(expectedList.get(1).getQuestion(), questionAndAnswerList.get(1).getQuestion());
    }
}
