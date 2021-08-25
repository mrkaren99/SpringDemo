package com.example.springdemo.service.impl;

import com.example.springdemo.model.Book;
import com.example.springdemo.model.Hashtag;
import com.example.springdemo.repository.BookRepository;
import com.example.springdemo.security.CurrentUser;
import com.example.springdemo.service.BookService;
import com.example.springdemo.service.HashtagService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final HashtagService hashtagService;

    @Value("${upload.dir}")
    private String uploadDir;

    @Override
    public List<Book> findAllBooks() {
        return bookRepository.findAll();
    }


    @Override
    public void addBook(Book book, MultipartFile multipartFile, CurrentUser currentUser) throws IOException {

        book.setUser(currentUser.getUser());

        if (!multipartFile.isEmpty()) {
            String picUrl = System.currentTimeMillis() + "_" + multipartFile.getOriginalFilename();
            multipartFile.transferTo(new File(uploadDir + File.separator + picUrl));
            book.setPicUrl(picUrl);
        }
        List<String> hashtagList = book.getHashtagList();
        List<Hashtag> hashtags = new ArrayList<>();
        for (String s : hashtagList) {
            Hashtag byName = hashtagService.findByName(s);
            hashtags.add(byName);
        }
        book.setHashtags(hashtags);


        book.setCreatedDate(new Date());


        bookRepository.save(book);
    }

    @Override
    public Optional<Book> findBookById(int id) {
        return bookRepository.findById(id);
    }
}
