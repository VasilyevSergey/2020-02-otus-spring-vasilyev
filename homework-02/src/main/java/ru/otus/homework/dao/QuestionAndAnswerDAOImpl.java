package ru.otus.homework.dao;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import ru.otus.homework.domain.QuestionAndAnswer;
import ru.otus.homework.exceptions.QuestionsLoadingException;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class QuestionAndAnswerDAOImpl implements QuestionAndAnswerDAO {
    @Value("${qna.filepath}")
    private String pathToCSV;

    @Value("${locale}")
    private String locale;

    public QuestionAndAnswerDAOImpl() {
    }

    private static final String[] FILE_HEADER_DEFAULT = {"Question", "Answer"};
    private static final String QUESTION = "Question";
    private static final String ANSWER = "Answer";

    @Override
    public List<QuestionAndAnswer> questionAndAnswerList() throws QuestionsLoadingException {
        ClassLoader cLoader = this.getClass().getClassLoader();
        URL url = cLoader.getResource(pathToCSV);

        CSVFormat csvFormat = CSVFormat.DEFAULT.withHeader(FILE_HEADER_DEFAULT);

        List<QuestionAndAnswer> questionsAndAnswerList;
        CSVParser parser = null;
        try {
            parser = CSVParser.parse(url, StandardCharsets.UTF_8, csvFormat);
            questionsAndAnswerList = parser.getRecords().stream().map(csvRecord -> {
                return new QuestionAndAnswer(csvRecord.get(QUESTION), csvRecord.get(ANSWER));
            }).collect(Collectors.toList());
        } catch (IllegalArgumentException | IOException e) {
            throw new QuestionsLoadingException(String.format("Ошибка при чтении csv файла: %s", e.getMessage()), e.getCause());
        } finally {
            if (parser != null) {
                try {
                    parser.close();
                } catch (IOException e) {
                    throw new QuestionsLoadingException("Ошибка при закритии парсера", e.getCause());
                }
            }
        }

        return questionsAndAnswerList;
    }
}
