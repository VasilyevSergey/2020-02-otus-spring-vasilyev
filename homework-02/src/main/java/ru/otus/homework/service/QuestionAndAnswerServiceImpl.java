package ru.otus.homework.service;

import org.springframework.stereotype.Service;
import ru.otus.homework.dao.QuestionAndAnswerDAO;
import ru.otus.homework.domain.Person;
import ru.otus.homework.domain.QuestionAndAnswer;
import ru.otus.homework.exceptions.QuestionsLoadingException;

import java.util.List;

@Service
public class QuestionAndAnswerServiceImpl implements QuestionAndAnswerService {

    private final QuestionAndAnswerDAO dao;
    private final InputOutputService ioService;
    private final PersonService personService;
    private final LocalizationService localizationService;

    public QuestionAndAnswerServiceImpl(QuestionAndAnswerDAO dao,
                                        InputOutputService ioService,
                                        PersonService personService,
                                        LocalizationService localizationService) {
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
        List<QuestionAndAnswer> questionAndAnswerList;

        try {
            questionAndAnswerList = getQuestionAndAnswerList();
        } catch (QuestionsLoadingException e) {
            ioService.showMessage(e.getMessage() + e.getCause());
            return;
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

        ioService.showMessage(
                localizationService.localizeMessage(
                        "test.result",
                        person.getFirstName() + " " + person.getLastName(),
                        String.valueOf(counterOfCorrectAnswers),
                        String.valueOf(questionAndAnswerList.size())
                ));
    }
}
