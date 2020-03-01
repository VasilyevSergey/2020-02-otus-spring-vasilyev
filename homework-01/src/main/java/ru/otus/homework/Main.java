package ru.otus.homework;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.homework.service.QuestionAndAnswerService;

public class Main {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");

        QuestionAndAnswerService service = context.getBean(QuestionAndAnswerService.class);

        service.startTest();
    }
}
