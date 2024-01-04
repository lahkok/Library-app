package com.example.tugaspresentasi.ui.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.tugaspresentasi.data.models.Book;
import com.example.tugaspresentasi.data.repositories.BookRepository;

import java.util.List;

public class BookViewModel extends AndroidViewModel {
    private final BookRepository bookRepository;

    public BookViewModel(@NonNull Application application) {
        super(application);
        bookRepository = new BookRepository(application);
    }

    public void refreshBooks() {
        getAllBooks();
    }
    public List<Book> getAllBooks() {
        return bookRepository.getAllBooks();
    }

    public Book getBookById(int id) {
        return bookRepository.findBookById(id);
    }

    public boolean updateBook(Book book) {
        return bookRepository.updateBook(book);
    }

    public boolean deleteBook(int bookId) {
        return bookRepository.delete(bookId);
    }

    public List<Book> getBooksByUser(int userId) {
        return bookRepository.getBooksByUser(userId);
    }
}
