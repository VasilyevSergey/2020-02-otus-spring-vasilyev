package com.otus.homework.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "meetings")
public class Meeting {
    @Id
    private String id;

    private String theme;

    private LocalDateTime meetingsDateTime;

    private String description;

    @DBRef
    private List<Book> bookList;

    @DBRef
    private BookClub bookClub;

    private Double longitude;

    private Double latitude;

    @DBRef
    private List<User> participantList;

    public Meeting(String theme,
                   LocalDateTime meetingsDateTime,
                   String description,
                   List<Book> bookList,
                   BookClub bookClub,
                   Double longitude,
                   Double latitude,
                   List<User> participantList) {
        this.theme = theme;
        this.meetingsDateTime = meetingsDateTime;
        this.description = description;
        this.bookList = bookList;
        this.bookClub = bookClub;
        this.longitude = longitude;
        this.latitude = latitude;
        this.participantList = participantList;
    }
}
