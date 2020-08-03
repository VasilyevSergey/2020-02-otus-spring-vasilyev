package com.otus.homework.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "authors")
public class AuthorSQL {
    @Id
    private String id;

    private String name;

    public AuthorSQL(String name) {
        this.name = name;
    }
}
