package ru.otus.homework.service;

import ru.otus.homework.dao.QuestionAndAnswerDAO;
import ru.otus.homework.domain.Person;
import ru.otus.homework.domain.QuestionAndAnswer;
import ru.otus.homework.exceptions.QuestionsLoadingException;

import java.util.ArrayList;
import java.util.List;

public class QuestionAndAnswerServiceImpl implements QuestionAndAnswerService {

    private final QuestionAndAnswerDAO dao;
    private final InputOutputServiceImpl ioService;
    private final PersonServiceImpl personService;

    public QuestionAndAnswerServiceImpl(QuestionAndAnswerDAO dao,
                                        InputOutputServiceImpl ioService,
                                        PersonServiceImpl personService) {
        this.dao = dao;
        this.ioService = ioService;
        this.personService = personService;
    }

    private List<QuestionAndAnswer> getQuestionAndAnswerList() throws QuestionsLoadingException {
        List<QuestionAndAnswer> questionAndAnswerList;
        questionAndAnswerList = dao.questionAndAnswerList();
        return questionAndAnswerList;
    }

    @Override
    public void startTest() {
        List<QuestionAndAnswer> questionAndAnswerList = new ArrayList<>();

        try {
            questionAndAnswerList = getQuestionAndAnswerList();
        } catch (QuestionsLoadingException e) {
            ioService.showMessage(e.getMessage() + e.getCause());
            return;
        }

        ioService.showMessage("Test");
        Person person = personService.getPerson();

        int counterOfCorrectAnswers = 0;
        for (QuestionAndAnswer qna : questionAndAnswerList) {
            ioService.showMessage(qna.getQuestion());
            String personAnswer = ioService.getMessage();
            if (personAnswer.equals(qna.getAnswer())) {
                counterOfCorrectAnswers++;
            }
        }

        ioService.showMessage(String.format(
                "%s, your result is %d/%d",
                person.getFirstName() + " " + person.getLastName(),
                counterOfCorrectAnswers,
                questionAndAnswerList.size()));
    }
}
