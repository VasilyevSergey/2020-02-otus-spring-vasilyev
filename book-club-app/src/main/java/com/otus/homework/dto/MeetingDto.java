package com.otus.homework.dto;

import com.otus.homework.domain.Book;
import com.otus.homework.domain.BookClub;
import com.otus.homework.domain.Meeting;
import com.otus.homework.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO встречи
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MeetingDto {
    private String id;
    private String theme;
    private LocalDateTime meetingsDateTime;
    private String description;
    private List<Book> bookList;
    private BookClub bookClub;
    private String address;
    private Double longitude;
    private Double latitude;
    private User admin;
    private List<User> participantList;

    public static MeetingDto toDto(Meeting meeting) {
        return new MeetingDto(
                meeting.getId(),
                meeting.getTheme(),
                meeting.getMeetingsDateTime(),
                meeting.getDescription(),
                meeting.getBookList(),
                meeting.getBookClub(),
                meeting.getAddress(),
                meeting.getLongitude(),
                meeting.getLatitude(),
                meeting.getAdmin(),
                meeting.getParticipantList());
    }

    public Meeting toEntity() {
        return new Meeting(
                this.getId(),
                this.getTheme(),
                this.getMeetingsDateTime(),
                this.getDescription(),
                this.getBookList(),
                this.getBookClub(),
                this.getAddress(),
                this.getLongitude(),
                this.getLatitude(),
                this.getAdmin(),
                this.getParticipantList());
    }
}
