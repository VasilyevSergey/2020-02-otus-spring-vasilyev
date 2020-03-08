package ru.otus.homework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.homework.dao.QuestionAndAnswerDAO;
import ru.otus.homework.domain.Person;
import ru.otus.homework.domain.QuestionAndAnswer;
import ru.otus.homework.exceptions.QuestionsLoadingException;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionAndAnswerServiceImpl implements QuestionAndAnswerService {

    private QuestionAndAnswerDAO dao;
    private InputOutputServiceImpl ioService;
    private PersonServiceImpl personService;
    private LocalizationServiceImpl localizationService;

    @Autowired
    public QuestionAndAnswerServiceImpl(QuestionAndAnswerDAO dao,
                                        InputOutputServiceImpl ioService,
                                        PersonServiceImpl personService,
                                        LocalizationServiceImpl localizationService) {
        this.dao = dao;
        this.ioService = ioService;
        this.personService = personService;
        this.localizationService = localizationService;
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
        }

        ioService.showMessage(localizationService.localizeMessage("test.name"));
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
