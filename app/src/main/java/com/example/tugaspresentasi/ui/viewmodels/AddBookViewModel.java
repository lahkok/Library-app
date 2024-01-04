package com.example.tugaspresentasi.ui.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import com.example.tugaspresentasi.data.models.Book;
import com.example.tugaspresentasi.data.repositories.BookRepository;

public class AddBookViewModel extends AndroidViewModel {
    private final BookRepository bookRepository;

    public AddBookViewModel(@NonNull Application application) {
        super(application);
        bookRepository = new BookRepository(application);
    }

    public long insertBook(Book book) {
        return bookRepository.insertBook(book);
    }
}
