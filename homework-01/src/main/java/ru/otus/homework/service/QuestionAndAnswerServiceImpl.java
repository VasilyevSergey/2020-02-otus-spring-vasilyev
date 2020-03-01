package ru.otus.homework.service;

import ru.otus.homework.dao.QuestionAndAnswerDAO;
import ru.otus.homework.domain.QuestionAndAnswer;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class QuestionAndAnswerServiceImpl implements QuestionAndAnswerService {

    private final QuestionAndAnswerDAO dao;

    public QuestionAndAnswerServiceImpl(QuestionAndAnswerDAO dao) {
        this.dao = dao;
    }

    @Override
    public void startTest() {
        Scanner in = new Scanner(System.in);

        List<QuestionAndAnswer> questionAndAnswerList = null;
        try {
            questionAndAnswerList = dao.questionAndAnswerList();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        System.out.println("Test");
        System.out.println("Enter your first and last name");
        String firstName = in.next();
        String LastName = in.next();

        int counterOfCorrectAnswers = 0;
        for (QuestionAndAnswer qna : questionAndAnswerList) {
            System.out.println(qna.getQuestion());
            String userAnswer = in.next();
            if (userAnswer.equals(qna.getAnswer())) {
                counterOfCorrectAnswers++;
            }
        }
        in.close();

        System.out.println(String.format(
                "%s, your result is %d/%d",
                firstName + " " + LastName,
                counterOfCorrectAnswers,
                questionAndAnswerList.size()));
    }
}
