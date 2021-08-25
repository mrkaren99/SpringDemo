package com.example.springdemo.controller;

import com.example.springdemo.model.Book;
import com.example.springdemo.model.User;
import com.example.springdemo.security.CurrentUser;
import com.example.springdemo.service.HashtagService;
import com.example.springdemo.service.UserService;
import com.example.springdemo.service.impl.BookServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class BookController {

    private final BookServiceImpl bookService;
    private final UserService userService;
    private final HashtagService hashtagService;

    @GetMapping("/books")
    public String books(ModelMap modelMap) {
        List<Book> all = bookService.findAllBooks();
        modelMap.addAttribute("books", all);
        return "books";
    }

    @GetMapping("/books/add")
    public String addBookPage(ModelMap modelMap) {
        List<User> all = userService.findAllUsers();
        modelMap.addAttribute("users", all);
        modelMap.addAttribute("hashtags", hashtagService.findAllHashtags());
        return "addBook";
    }

    @PostMapping("/books/add")
    public String addBook(@ModelAttribute Book book,
                          @RequestParam("picture") MultipartFile multipartFile,
                          @AuthenticationPrincipal CurrentUser currentUser
    ) throws IOException {
        bookService.addBook(book, multipartFile, currentUser);
        return "redirect:/books";
    }

    @GetMapping("/books/{id}")
    public String singleBook(@PathVariable("id") int id, ModelMap modelMap) {
        Optional<Book> book = bookService.findBookById(id);
        if (!book.isPresent()) {
            return "redirect:/";
        }
        modelMap.addAttribute("book", book.get());
        return "singleBook";
    }


}
