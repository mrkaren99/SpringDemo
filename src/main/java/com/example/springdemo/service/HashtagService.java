package com.example.springdemo.service;

import com.example.springdemo.model.Hashtag;

import java.util.List;

public interface HashtagService {

    Hashtag findByName(String name);

    List<Hashtag> findAllHashtags();
}
