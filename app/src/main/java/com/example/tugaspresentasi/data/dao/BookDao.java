package com.example.tugaspresentasi.data.dao;

import com.example.tugaspresentasi.data.models.Book;

import java.util.List;

public interface BookDao {
    long insert(Book book);
    boolean update(Book book);
    boolean delete(int bookId);
    Book findBookById(int bookId);
    List<Book> getAllBooks();

    List<Book> getBooksByUser(int userId);
}
