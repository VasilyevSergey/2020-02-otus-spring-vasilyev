package com.otus.homework.config;

import com.otus.homework.service.InputOutputServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.InputStream;
import java.io.PrintStream;

@Configuration
public class InputOutputServiceConfig {

    private static final InputStream IN_STREAM = System.in;
    private static final PrintStream OUT_STREAM = System.out;

    @Bean
    public InputOutputServiceImpl inputOutputService() {
        return new InputOutputServiceImpl(IN_STREAM, OUT_STREAM);
    }
}
