package com.example.hemistatunfbot.Student;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Student    {
    @Id
    private Long ChatId;

    private String firstName;

    private String lastName;

    private String userName;

    @ManyToOne
    Group group;
}
