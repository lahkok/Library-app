package com.example.tugaspresentasi.ui.view;

import static android.app.PendingIntent.getActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.tugaspresentasi.data.models.Book;
import com.example.tugaspresentasi.ui.viewmodels.AddBookViewModel;
import com.github.dhaval2404.imagepicker.ImagePicker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.tugaspresentasi.R;

public class AddBookActivity extends AppCompatActivity {
    private EditText editTextTitle;
    private EditText editTextAuthor;
    private EditText editTextDescription;
    private Button buttonAddBook;
    private ImageView imageViewCover;
    private Uri fileUri;
    private AddBookViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        editTextTitle = findViewById(R.id.edit_text_title);
        editTextAuthor = findViewById(R.id.edit_text_author);
        editTextDescription = findViewById(R.id.edit_text_description);
        buttonAddBook = findViewById(R.id.button_add_book);
        imageViewCover = findViewById(R.id.image_view_cover);

        viewModel = new ViewModelProvider(this).get(AddBookViewModel.class);

        buttonAddBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addBook();
            }
        });

        imageViewCover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.with(AddBookActivity.this)
                        .crop()	    			                //Crop image(Optional), Check Customization for more option
                        .compress(1024)			        //Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
            }
        });
    }

    private void addBook() {
        String title = editTextTitle.getText().toString().trim();
        String author = editTextAuthor.getText().toString().trim();
        String description = editTextDescription.getText().toString().trim();

        if (title.isEmpty() || author.isEmpty()) {
            Toast.makeText(this, "Please enter a title and author", Toast.LENGTH_SHORT).show();
            return;
        }

        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        int userId = sharedPreferences.getInt("userId", -1);
        Log.d("AddBook", "userId: " + userId);

        if (userId == -1) {
            // Handle the case where the userId is not found in SharedPreferences
            Toast.makeText(this, "You need to log in to add a book", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, LoginActivity.class); // Replace LoginActivity with your login activity class
            startActivity(intent);
            return;
        }

        Book newBook = new Book();
        newBook.setTitle(title);
        newBook.setAuthor(author);
        newBook.setDescription(description);
        newBook.setCoverImageUri(fileUri);
        newBook.setUserId(userId);

        viewModel.insertBook(newBook);

        Toast.makeText(this, "Book added", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            fileUri = data.getData();
            imageViewCover.setImageURI(fileUri);
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show();
        }
    }
}
