package com.example.tugaspresentasi.data.repositories;

import android.content.Context;

import com.example.tugaspresentasi.config.DatabaseConfig;
import com.example.tugaspresentasi.data.dao.BookDao;
import com.example.tugaspresentasi.data.dao.BookDaoSQLite;
import com.example.tugaspresentasi.data.models.Book;

import java.util.List;

public class BookRepository {
    private final BookDao bookDao;

    public BookRepository(Context context) {
        bookDao = new BookDaoSQLite(context);
    }

    public long insertBook(Book book) {
        return bookDao.insert(book);
    }

    public boolean updateBook(Book book) {
        return bookDao.update(book);
    }

    public Book findBookById(int bookId) {
        return bookDao.findBookById(bookId);
    }

    public List<Book> getAllBooks() {
        return bookDao.getAllBooks();
    }

    public boolean delete(int bookId) {return bookDao.delete(bookId);}

    public List<Book> getBooksByUser(int userId) {
        return bookDao.getBooksByUser(userId);
    }
}
