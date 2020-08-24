package com.otus.homework.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "book_clubs")
public class BookClub {
    @Id
    private String id;

    @Field("name")
    private String name;

    @Field("mainTheme")
    private String mainTheme;

    @DBRef
    @Field("admin")
    private User admin;

    @DBRef
    @Field("participantList")
    private List<User> participantList;

    public BookClub(String name,
                    User admin,
                    String mainTheme,
                    List<User> participantList) {
        this.name = name;
        this.admin = admin;
        this.mainTheme = mainTheme;
        this.participantList = participantList;
    }
}
