package ru.otus.homework;

import org.springframework.context.annotation.*;
import ru.otus.homework.service.QuestionAndAnswerService;

@PropertySource("classpath:application.properties")
@Configuration
@ComponentScan
public class Main {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Main.class);

        QuestionAndAnswerService service = context.getBean(QuestionAndAnswerService.class);

        service.startTest();
    }
}
