package com.example.springdemo.service;

import com.example.springdemo.model.Book;
import com.example.springdemo.security.CurrentUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface BookService {

    Page<Book> findAllBooks(PageRequest pageRequest);

    void addBook(Book book, MultipartFile multipartFile, CurrentUser currentUser);

    Optional<Book> findBookById(int id);
}
