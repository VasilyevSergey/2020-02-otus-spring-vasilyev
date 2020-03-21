package com.otus.homework;

import com.otus.homework.service.QuestionAndAnswerService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class TestingApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context =  SpringApplication.run(TestingApplication.class, args);

		QuestionAndAnswerService service = context.getBean(QuestionAndAnswerService.class);

		service.startTest();
	}
}
