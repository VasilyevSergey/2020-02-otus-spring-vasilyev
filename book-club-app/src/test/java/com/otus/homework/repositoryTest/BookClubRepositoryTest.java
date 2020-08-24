package com.otus.homework.repositoryTest;

import com.otus.homework.domain.BookClub;
import com.otus.homework.domain.Meeting;
import com.otus.homework.repository.BookClubRepository;
import com.otus.homework.repository.MeetingRepository;
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
@ComponentScan({"com.otus.homework.config", "com.otus.homework.events"})
@DisplayName("Dao для работы с книжными клубами ")
class BookClubRepositoryTest {
    private static final String LOTR_BOOK_CLUB = "LOTR book club";
    private static final String LOTR_UNOFFICIAL_BOOK_CLUB = "LOTR unofficial book club";
    private static final String FRAGMENT = "LOTR";

    @Autowired
    private BookClubRepository bookClubRepository;

    @Autowired
    private MeetingRepository meetingRepository;

    @MockBean
    private MongoUserDetailsServiceImpl userDetailsService;

    @DisplayName("возвращать все клубы по фрагменту названия")
    @Test
    void shouldFindAllBookClubsByNameFragment() {
        List<BookClub> bookClubList = bookClubRepository.findAllByNameContaining(FRAGMENT);

        List<String> expectedBookClubNameList = Arrays.asList(LOTR_BOOK_CLUB, LOTR_UNOFFICIAL_BOOK_CLUB);

        assertThat(bookClubList.size()).isEqualTo(2);
        assertThat(bookClubList.stream()
                               .map(BookClub::getName)
                               .collect(Collectors.toList()))
                .containsAll(expectedBookClubNameList);
    }


    @DisplayName("удалять клуб и его встречи")
    @Test
    void shouldDeleteBookClubAndMeetings() {
        // загружаем клуб и встречи клуба
        BookClub bookClub = bookClubRepository.findAllByNameContaining(LOTR_BOOK_CLUB).get(0);

        List<Meeting> meetingList = meetingRepository.findAllByBookClub(bookClub);
        assertThat(meetingList.size()).isGreaterThan(0);

        // удаляем клуб
        bookClubRepository.deleteById(bookClub.getId());

        // проверяем, что клуб и встречи удалены
        List<BookClub> bookClubList = bookClubRepository.findAllByNameContaining(LOTR_BOOK_CLUB);
        assertThat(bookClubList.size()).isEqualTo(0);

        meetingList = meetingRepository.findAllByBookClub(bookClub);
        assertThat(meetingList.size()).isEqualTo(0);
    }
}
