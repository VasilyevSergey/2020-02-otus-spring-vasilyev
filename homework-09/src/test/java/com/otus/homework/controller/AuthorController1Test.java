package com.otus.homework.controller;

import com.otus.homework.service.AuthorService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.OverrideAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

//@RunWith(SpringRunner.class)
@WebMvcTest(AuthorController.class)
@OverrideAutoConfiguration(enabled=true)
class AuthorController1Test {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AuthorService authorService;

    @Test
    void test()  {
        System.out.println("1");
    }
}
