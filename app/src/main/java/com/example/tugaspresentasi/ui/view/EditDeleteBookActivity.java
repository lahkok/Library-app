package com.example.tugaspresentasi.ui.view;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.tugaspresentasi.R;
import com.example.tugaspresentasi.data.models.Book;
import com.example.tugaspresentasi.ui.viewmodels.BookViewModel;

public class EditDeleteBookActivity extends AppCompatActivity {
    private int bookId;
    private Book book;
    private BookViewModel viewModel;
    private EditText titleEditText;
    private EditText authorEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        viewModel = new ViewModelProvider(this).get(BookViewModel.class);

        titleEditText = findViewById(R.id.titleEditText);
        authorEditText = findViewById(R.id.authorEditText);

        bookId = getIntent().getIntExtra("BOOK_ID", -1);
        if (bookId != -1) {
            book = viewModel.getBookById(bookId);
            if (book != null) {
                ImageView bookCover = findViewById(R.id.book_cover);
                bookCover.setImageURI((book.getCoverImageUri()));

                titleEditText.setText(book.getTitle());
                authorEditText.setText(book.getAuthor());
            }
        }

        Button updateButton = findViewById(R.id.update_button);
        updateButton.setOnClickListener(this::onUpdateButtonClick);

        Button deleteButton = findViewById(R.id.delete_button);
        deleteButton.setOnClickListener(view -> {
            if (bookId != -1) {
                new AlertDialog.Builder(this)
                        .setTitle("Delete Book")
                        .setMessage("Are you sure you want to delete this book?")
                        .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                            viewModel.deleteBook(bookId);
                            finish();  // Close the Activity
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });
    }

    public void onUpdateButtonClick(View view) {
        if (bookId != -1) {
            book.setTitle(titleEditText.getText().toString());
            book.setAuthor(authorEditText.getText().toString());
            // Update other fields...

            boolean success = viewModel.updateBook(book);
            if (success) {
                Toast.makeText(this, "Book updated successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, HomepageActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Failed to update book", Toast.LENGTH_SHORT).show();
            }
        }
    }
}