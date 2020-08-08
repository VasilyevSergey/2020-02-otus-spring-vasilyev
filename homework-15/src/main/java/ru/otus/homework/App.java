package ru.otus.homework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import ru.otus.homework.service.DryCleaningService;

@SpringBootApplication
public class App {

    public static void main(String[] args) throws Exception {
        ApplicationContext ctx = SpringApplication.run(App.class);

        DryCleaningService service = ctx.getBean(DryCleaningService.class);
        service.startWork();
    }
}
