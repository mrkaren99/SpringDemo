package com.example.springdemo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;

    private String authorName;
    private double price;
    private Date createdDate;
    private String picUrl;
    @ManyToOne
    private User user;
    @ManyToMany
    private List<Hashtag> hashtags;

    @Transient
    private List<String> hashtagList;

}
