package com.otus.homework.config;

import com.otus.homework.domain.Author;
import com.otus.homework.domain.Book;
import com.otus.homework.domain.Genre;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.data.MongoItemReader;
import org.springframework.batch.item.data.builder.MongoItemReaderBuilder;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
public class JobConfig {
    private static final int CHUNK_SIZE = 5;
    private final Logger logger = LoggerFactory.getLogger("Batch");

    private static final String AUTHOR_READER = "authorReader";
    private static final String IMPORT_AUTHOR_STEP = "importAuthorStep";
    private static final String GENRE_READER = "genreReader";
    private static final String IMPORT_GENRE_STEP = "importGenreStep";
    private static final String BOOK_READER = "bookReader";
    private static final String IMPORT_BOOK_STEP = "importBookStep";
    private static final String IMPORT_JOB = "importJob";

    private static final String QUERY_INSERT_AUTHORS = "insert into authors (id, name) values (:id, :name)";
    private static final String QUERY_INSERT_GENRES = "insert into genres (id, name) values (:id, :name)";
    private static final String QUERY_INSERT_BOOKS = "insert into books (id, title, author_id, genre_id) values (:id, :title, :author.id, :genre.id)";

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    public JobConfig(JobBuilderFactory jobBuilderFactory,
                     StepBuilderFactory stepBuilderFactory) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
    }

    // import authors
    @StepScope
    @Bean
    public MongoItemReader<Author> authorReader(MongoTemplate template) {
        return new MongoItemReaderBuilder<Author>()
                .name(AUTHOR_READER)
                .template(template)
                .jsonQuery("{}")
                .targetType(Author.class)
                .sorts(new HashMap<>())
                .build();
    }

    @StepScope
    @Bean
    public JdbcBatchItemWriter<Author> authorWriter(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Author>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .dataSource(dataSource)
                .sql(QUERY_INSERT_AUTHORS)
                .build();
    }

    @Bean
    public Step importAuthorStep(MongoItemReader authorReader, JdbcBatchItemWriter authorWriter) {
        return stepBuilderFactory.get(IMPORT_AUTHOR_STEP)
                .chunk(CHUNK_SIZE)
                .reader(authorReader)
                .writer(authorWriter)
                .build();
    }

    // import genres
    @StepScope
    @Bean
    public MongoItemReader<Genre> genreReader(MongoTemplate template) {
        return new MongoItemReaderBuilder<Genre>()
                .name(GENRE_READER)
                .template(template)
                .jsonQuery("{}")
                .targetType(Genre.class)
                .sorts(new HashMap<>())
                .build();
    }

    @StepScope
    @Bean
    public JdbcBatchItemWriter<Genre> genreWriter(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Genre>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .dataSource(dataSource)
                .sql(QUERY_INSERT_GENRES)
                .build();
    }

    @Bean
    public Step importGenreStep(MongoItemReader genreReader, JdbcBatchItemWriter genreWriter) {
        return stepBuilderFactory.get(IMPORT_GENRE_STEP)
                .chunk(CHUNK_SIZE)
                .reader(genreReader)
                .writer(genreWriter)
                .build();
    }

    // import books
    @StepScope
    @Bean
    public MongoItemReader<Book> bookReader(MongoTemplate template) {
        return new MongoItemReaderBuilder<Book>()
                .name(BOOK_READER)
                .template(template)
                .jsonQuery("{}")
                .targetType(Book.class)
                .sorts(new HashMap<>())
                .build();
    }

    @StepScope
    @Bean
    public JdbcBatchItemWriter<Book> bookWriter(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Book>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .dataSource(dataSource)
                .sql(QUERY_INSERT_BOOKS)
                .build();
    }

    @Bean
    public Step importBookStep(MongoItemReader bookReader, JdbcBatchItemWriter bookWriter) {
        return stepBuilderFactory.get(IMPORT_BOOK_STEP)
                .chunk(CHUNK_SIZE)
                .reader(bookReader)
                .writer(bookWriter)
                .build();
    }

    @Bean
    public Job importJob(Step importAuthorStep, Step importGenreStep, Step importBookStep) {
        return jobBuilderFactory.get(IMPORT_JOB)
                .incrementer(new RunIdIncrementer())
                .flow(importAuthorStep)
                .next(importGenreStep)
                .next(importBookStep)
                .end()
                .listener(new JobExecutionListener() {
                    @Override
                    public void beforeJob(JobExecution jobExecution) {
                        logger.info("Начало job");
                    }

                    @Override
                    public void afterJob(JobExecution jobExecution) {
                        logger.info("Конец job");
                    }
                })
                .build();
    }
}
