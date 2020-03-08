package ru.otus.homework.dao;

import ru.otus.homework.domain.QuestionAndAnswer;
import ru.otus.homework.exceptions.QuestionsLoadingException;

import java.util.List;

public interface QuestionAndAnswerDAO {
    List<QuestionAndAnswer> questionAndAnswerList() throws QuestionsLoadingException;
}
