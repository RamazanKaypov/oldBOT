package com.example.hemistatunfbot.Student;

import com.example.hemistatunfbot.IMAGE.Image;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "groups")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @Column(nullable = false)
    private String groupName;

    @ManyToOne  (cascade = CascadeType.ALL)
    Image image;



    @Override
    public String toString() {
        return "Group{" +
                "id=" + id +
                ", groupName='" + groupName + '\'' +
                ", image=" + image +
                '}';
    }
}
