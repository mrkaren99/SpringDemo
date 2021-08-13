package com.example.springdemo.controller;

import com.example.springdemo.model.Book;
import com.example.springdemo.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Controller
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @GetMapping("/books")
    public String books(ModelMap modelMap) {
        List<Book> all = bookRepository.findAll();
        modelMap.addAttribute("books", all);
        return "books";
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
