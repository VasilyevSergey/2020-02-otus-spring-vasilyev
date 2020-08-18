package com.otus.homework.repositoryTest;

import com.otus.homework.domain.BookClub;
import com.otus.homework.repository.BookClubRepository;
import com.otus.homework.service.MongoUserDetailsServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
@EnableConfigurationProperties
@ComponentScan({"com.otus.homework.config"})
@DisplayName("Dao для работы с книжными клубами ")
class BookClubRepositoryTest {
    @Autowired
    private BookClubRepository bookClubRepository;

    @MockBean
    private MongoUserDetailsServiceImpl userDetailsService;

    @DisplayName("возвращать все клубы по фрагменту названия")
    @Test
    void shouldFindAllBookClubsByNameFragment() {
        List<BookClub> bookClubList = bookClubRepository.findAllByNameContaining("LOTR");

        List<String> expectedBookClubNameList = Arrays.asList("LOTR book club", "LOTR unofficial book club");

        assertThat(bookClubList.size()).isEqualTo(2);
        assertThat(bookClubList.stream()
                               .map(BookClub::getName)
                               .collect(Collectors.toList()))
                .containsAll(expectedBookClubNameList);
    }
}
