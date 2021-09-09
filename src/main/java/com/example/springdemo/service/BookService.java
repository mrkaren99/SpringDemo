package com.example.springdemo.service;

import com.example.springdemo.model.Book;
import com.example.springdemo.security.CurrentUser;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface BookService {

    List<Book> findAllBooks();

    void addBook(Book book, MultipartFile multipartFile, CurrentUser currentUser);

    Optional<Book> findBookById(int id);
}
