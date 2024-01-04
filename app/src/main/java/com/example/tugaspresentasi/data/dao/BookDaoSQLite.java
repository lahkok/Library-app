package com.example.tugaspresentasi.data.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import com.example.tugaspresentasi.config.DatabaseConfig;
import com.example.tugaspresentasi.data.models.Book;

import java.util.ArrayList;
import java.util.List;

public class BookDaoSQLite implements BookDao{
    private final SQLiteDatabase database;

    public BookDaoSQLite(Context context) {
        DatabaseConfig databaseConfig = new DatabaseConfig(context);
        database = databaseConfig.getWritableDatabase();
    }

    @Override
    public long insert(Book book) {
        ContentValues values = new ContentValues();
        values.put("book_title", book.getTitle());
        values.put("book_author", book.getAuthor());
        values.put("book_description", book.getDescription());
        values.put("user_id", book.getUserId());

        Log.i("BookDaoSQLite", "Cover image URI (before if): " + (book.getCoverImageUri() != null ? book.getCoverImageUri().toString() : "null"));
        if (book.getCoverImageUri() != null) {
            Log.i("BookDaoSQLite", "Cover image URI: " + book.getCoverImageUri().toString());
            values.put("cover_image_uri", book.getCoverImageUri().toString());
        }

        long newRowId = database.insert("books", null, values);

        if (newRowId == -1) {
            Log.e("BookDaoSQLite", "Error inserting book");
        } else {
            Log.i("BookDaoSQLite", "Book inserted with row ID: " + newRowId);
        }

        return newRowId;
    }

    @Override
    public boolean update(Book book) {
        ContentValues values = new ContentValues();
        values.put("book_title", book.getTitle());
        values.put("book_author", book.getAuthor());
        values.put("book_description", book.getDescription());
        if (book.getCoverImageUri() != null) {
            values.put("cover_image_uri", book.getCoverImageUri().toString());
        }

        return database.update("books", values, "book_id = ?",
                new String[]{String.valueOf(book.getId())}) > 0;
    }

    @Override
    public boolean delete(int bookId) {
        return database.delete("books", "book_id = ?", new String[]{String.valueOf(bookId)}) > 0;
    }

    @Override
    public Book findBookById(int bookId) {
        Cursor cursor = database.query("books", null, "book_id = ?",
                new String[]{String.valueOf(bookId)}, null, null, null);

        Book book = null;
        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex("book_id");
            int titleIndex = cursor.getColumnIndex("book_title");
            int authorIndex = cursor.getColumnIndex("book_author");
            int descriptionIndex = cursor.getColumnIndex("book_description");
            int coverImageUriIndex = cursor.getColumnIndex("cover_image_uri");

            if (idIndex != -1 && titleIndex != -1 && authorIndex != -1 && descriptionIndex != -1 && coverImageUriIndex != -1) {
                book = new Book(
                        cursor.getInt(idIndex),
                        cursor.getString(titleIndex),
                        cursor.getString(authorIndex),
                        cursor.getString(descriptionIndex),
                        Uri.parse(cursor.getString(coverImageUriIndex))
                );
            }
        }

        cursor.close();
        return book;
    }

    @Override
    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        Cursor cursor = database.query("books", null, null, null, null, null, null);

        int idIndex = cursor.getColumnIndex("book_id");
        int titleIndex = cursor.getColumnIndex("book_title");
        int authorIndex = cursor.getColumnIndex("book_author");
        int descriptionIndex = cursor.getColumnIndex("book_description");
        int coverImageUriIndex = cursor.getColumnIndex("cover_image_uri");

        if (idIndex != -1 && titleIndex != -1 && authorIndex != -1 && descriptionIndex != -1 && coverImageUriIndex != -1) {
            while (cursor.moveToNext()) {
                String coverImageUriString = cursor.getString(coverImageUriIndex);
                Uri coverImageUri = coverImageUriString != null ? Uri.parse(coverImageUriString) : null;
                Book book = new Book(
                        cursor.getInt(idIndex),
                        cursor.getString(titleIndex),
                        cursor.getString(authorIndex),
                        cursor.getString(descriptionIndex),
                        coverImageUri
                );
                books.add(book);
            }
        }

        cursor.close();
        return books;
    }

    @Override
    public List<Book> getBooksByUser(int userId) {
        List<Book> books = new ArrayList<>();

        Cursor cursor = database.rawQuery("SELECT * FROM books WHERE user_id = ?",
                new String[]{String.valueOf(userId)});

        int idIndex = cursor.getColumnIndex("book_id");
        int titleIndex = cursor.getColumnIndex("book_title");
        int authorIndex = cursor.getColumnIndex("book_author");
        int descriptionIndex = cursor.getColumnIndex("book_description");
        int coverImageUriIndex = cursor.getColumnIndex("cover_image_uri");

        if (idIndex != -1 && titleIndex != -1 && authorIndex != -1 && descriptionIndex != -1 && coverImageUriIndex != -1) {
            while (cursor.moveToNext()) {
                // Create a new Book object and populate it with the data from the cursor
                Book book = new Book();
                book.setId(cursor.getInt(idIndex));
                book.setTitle(cursor.getString(titleIndex));
                book.setAuthor(cursor.getString(authorIndex));
                book.setDescription(cursor.getString(descriptionIndex));
                book.setCoverImageUri(Uri.parse(cursor.getString(coverImageUriIndex)));

                // Add the Book object to the list
                books.add(book);
            }
        }

        cursor.close();
        return books;
    }
}