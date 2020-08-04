package ru.otus.homework;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.AbstractApplicationContext;
import ru.otus.homework.service.DryCleaningService;

@ComponentScan
public class App {

    public static void main(String[] args) throws Exception {
        AbstractApplicationContext ctx = new AnnotationConfigApplicationContext(App.class);

        DryCleaningService service = ctx.getBean(DryCleaningService.class);
        service.startWork();
    }
}
