package com.example.springbootunitintergrationtest2.model;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Builder
public class UserClass {
    @Id
    @Column(length = 200)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long userNo;
    private String username;
    private String userAdd;
    private int age;
}
