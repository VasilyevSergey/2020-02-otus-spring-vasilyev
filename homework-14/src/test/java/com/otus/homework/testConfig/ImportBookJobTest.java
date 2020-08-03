package com.otus.homework.testConfig;

import com.otus.homework.dao.AuthorRepository;
import com.otus.homework.domain.AuthorSQL;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import java.util.ArrayList;
import java.util.List;

import static com.otus.homework.config.JobConfig.IMPORT_JOB;
import static org.assertj.core.api.Assertions.assertThat;

@ComponentScan("com.otus.homework.testConfig")
@SpringBootTest
@SpringBatchTest
class ImportBookJobTest {
    private static final List<AuthorSQL> EXPECTED_AUTHOR_LIST = List.of(
            new AuthorSQL("1", "Pushkin"),
            new AuthorSQL("2", "Tolkien"));

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private JobRepositoryTestUtils jobRepositoryTestUtils;

    @BeforeEach
    void clearMetaData() {
        jobRepositoryTestUtils.removeJobExecutions();
    }

    @Test
    void testJob() throws Exception {
        // Проверяем, что авторов в H2 еще нет
        assertThat(authorRepository.findAll())
                .isEqualTo(new ArrayList<>());

        // запускаем джоб
        Job job = jobLauncherTestUtils.getJob();
        assertThat(job).isNotNull()
                .extracting(Job::getName)
                .isEqualTo(IMPORT_JOB);

        JobExecution jobExecution = jobLauncherTestUtils.launchJob();

        assertThat(jobExecution.getExitStatus().getExitCode()).isEqualTo("COMPLETED");

        // проверяем, что в H2 пояились авторы
        assertThat(authorRepository.findAll())
                .isEqualTo(EXPECTED_AUTHOR_LIST);

    }
}