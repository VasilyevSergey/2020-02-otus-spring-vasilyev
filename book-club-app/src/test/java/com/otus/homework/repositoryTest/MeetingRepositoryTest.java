package com.otus.homework.repositoryTest;

import com.otus.homework.domain.Author;
import com.otus.homework.domain.Book;
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
@DisplayName("Dao для работы со встречами ")
class MeetingRepositoryTest {
    private static final String FIRST_EXPECTED_MEETING_THEME = "Monthly meeting of the LOTR club";
    private static final String SECOND_EXPECTED_MEETING_THEME = "Another meeting";
    private static final String CLUB_NAME = "LOTR book club";
    private static final Author TOLKIEN = new Author("2", "Tolkien");
    private static final Book LOTR =  new Book("2", "Lord of the Rings", TOLKIEN);

    @Autowired
    private MeetingRepository meetingRepository;

    @Autowired
    private BookClubRepository bookClubRepository;

    @MockBean
    private MongoUserDetailsServiceImpl userDetailsService;

    @DisplayName("возвращать все встречи клуба по фрагменту названия")
    @Test
    void shouldFindAllMeetingsByThemeFragment() {
        List<String> expectedMeetingNameList = Arrays.asList(FIRST_EXPECTED_MEETING_THEME, SECOND_EXPECTED_MEETING_THEME);

        BookClub bookClub = bookClubRepository.findAllByNameContaining(CLUB_NAME).get(0);

        List<Meeting> actualMeetingList = meetingRepository.findAllByBookClub(bookClub);
        List<String> actualMeetingNameList = actualMeetingList.stream()
                                                              .map(Meeting::getTheme)
                                                              .collect(Collectors.toList());
        assertThat(actualMeetingList.size()).isEqualTo(2);
        assertThat(actualMeetingNameList).containsAll(expectedMeetingNameList);
    }

    @DisplayName("возвращать все встречи клуба по фрагменту названия")
    @Test
    void shouldFindAllMeetingsByBookInBookList() {
        List<String> expectedMeetingNameList = Arrays.asList(FIRST_EXPECTED_MEETING_THEME, SECOND_EXPECTED_MEETING_THEME);

        List<Meeting> actualMeetingList = meetingRepository.findAllByBookListContaining(LOTR);
        List<String> actualMeetingNameList = actualMeetingList.stream()
                .map(Meeting::getTheme)
                .collect(Collectors.toList());

        assertThat(actualMeetingList.size()).isEqualTo(2);
        assertThat(actualMeetingNameList).containsAll(expectedMeetingNameList);
    }
}
