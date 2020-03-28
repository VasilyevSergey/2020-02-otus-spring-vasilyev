package com.otus.homework.dao;

import com.otus.homework.domain.QuestionAndAnswer;
import com.otus.homework.exceptions.QuestionsLoadingException;

import java.util.List;

public interface QuestionAndAnswerDAO {
    List<QuestionAndAnswer> questionAndAnswerList() throws QuestionsLoadingException;
}
