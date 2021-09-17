package com.example.springdemo.controller;

import com.example.springdemo.model.Book;
import com.example.springdemo.model.User;
import com.example.springdemo.security.CurrentUser;
import com.example.springdemo.service.HashtagService;
import com.example.springdemo.service.UserService;
import com.example.springdemo.service.impl.BookServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequiredArgsConstructor
@Slf4j
public class BookController {

    private final BookServiceImpl bookService;
    private final UserService userService;
    private final HashtagService hashtagService;

    @GetMapping("/books")
    public String books(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "2") int size,
            @RequestParam(value = "orderBy", defaultValue = "title") String orderBy,
            @RequestParam(value = "order", defaultValue = "ASC") String order,
            ModelMap modelMap, @AuthenticationPrincipal CurrentUser currentUser) {

        Sort sort = order.equals("ASC") ? Sort.by(Sort.Order.asc(orderBy)) : Sort.by(Sort.Order.desc(orderBy));


        PageRequest pageRequest = PageRequest.of(page - 1, size, sort);


        Page<Book> all = bookService.findAllBooks(pageRequest);
        int totalPages = all.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            modelMap.addAttribute("pageNumbers", pageNumbers);
        }
        modelMap.addAttribute("books", all);
        log.info("User with {} username opened books page, book.size = {}", currentUser.getUser().getEmail(), all.getSize());
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
