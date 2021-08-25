package com.example.springdemo.service.impl;

import com.example.springdemo.model.Hashtag;
import com.example.springdemo.repository.HashtagRepository;
import com.example.springdemo.service.HashtagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HashtagServiceImpl implements HashtagService {

    private final HashtagRepository hashtagRepository;

    @Override
    public Hashtag findByName(String name) {
        return hashtagRepository.findByName(name);
    }

    public List<Hashtag> findAllHashtags() {
        return hashtagRepository.findAll();
    }

}
