package com.example.springdemo.controller;

import com.example.springdemo.model.Book;
import com.example.springdemo.model.Hashtag;
import com.example.springdemo.model.User;
import com.example.springdemo.repository.BookRepository;
import com.example.springdemo.repository.HashtagRepository;
import com.example.springdemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HashtagRepository hashtagRepository;

    @Value("${upload.dir}")
    private String uploadDir;

    @GetMapping("/books")
    public String books(ModelMap modelMap) {
        List<Book> all = bookRepository.findAll();
        modelMap.addAttribute("books", all);
        return "books";
    }

    @GetMapping("/books/add")
    public String addBookPage(ModelMap modelMap) {
        List<User> all = userRepository.findAll();
        modelMap.addAttribute("users", all);
        modelMap.addAttribute("hashtags", hashtagRepository.findAll());
        return "addBook";
    }

    @PostMapping("/books/add")
    public String addBook(@ModelAttribute Book book,
                          @RequestParam("picture") MultipartFile multipartFile) throws IOException {
        String picUrl = System.currentTimeMillis() + "_" + multipartFile.getOriginalFilename();
        multipartFile.transferTo(new File(uploadDir + File.separator + picUrl));

        List<String> hashtagList = book.getHashtagList();
        List<Hashtag> hashtags = new ArrayList<>();
        for (String s : hashtagList) {
            Hashtag byName = hashtagRepository.findByName(s);
            hashtags.add(byName);
        }
        book.setHashtags(hashtags);

        book.setPicUrl(picUrl);
        book.setCreatedDate(new Date());
        bookRepository.save(book);
        return "redirect:/books";
    }

    @GetMapping("/books/{id}")
    public String singleBook(@PathVariable("id") int id, ModelMap modelMap) {
        Optional<Book> book = bookRepository.findById(id);
        if (!book.isPresent()) {
            return "redirect:/";
        }
        modelMap.addAttribute("book", book.get());
        return "singleBook";
    }


}
