package com.webproject;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "exam")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Exam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int userid;

    private String score;

    private String time;

    private String matscore;

    private String mattime;

    private String fenscore;

    private String fentime;
}
