package ru.otus.homework.dao;

import ru.otus.homework.domain.QuestionAndAnswer;

import java.io.IOException;
import java.util.List;

public interface QuestionAndAnswerDAO {
    List<QuestionAndAnswer> questionAndAnswerList() throws IOException;
}
