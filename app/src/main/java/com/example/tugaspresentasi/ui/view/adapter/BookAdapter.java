package com.example.tugaspresentasi.ui.view.adapter;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tugaspresentasi.R;
import com.example.tugaspresentasi.data.models.Book;
import com.example.tugaspresentasi.ui.view.EditDeleteBookActivity;
import com.example.tugaspresentasi.ui.viewmodels.BookViewModel;

import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {
    private List<Book> books;
    private final BookViewModel viewModel;
    private int maxItemCount = Integer.MAX_VALUE;

    public BookAdapter(List<Book> books, BookViewModel viewModel) {
        this.books = books;
        this.viewModel = viewModel;
    }

    public void setMaxItemCount(int maxItemCount) {
        this.maxItemCount = maxItemCount;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_item, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book book = books.get(position);
        Log.d("BookAdapter", "Binding book: " + book.getTitle());
        holder.bookTitle.setText(book.getTitle());
        holder.bookAuthor.setText(book.getAuthor());
        holder.bookCover.setImageURI(book.getCoverImageUri());
    }

    @Override
    public int getItemCount() {
        return Math.min(books.size(), maxItemCount);
    }

    public void setBooks(List<Book> books) {
        this.books = books;
        notifyDataSetChanged();
    }

    public class BookViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView bookTitle;
        TextView bookAuthor;
        ImageView bookCover;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            bookTitle = itemView.findViewById(R.id.book_title);
            bookAuthor = itemView.findViewById(R.id.book_author);
            bookCover = itemView.findViewById(R.id.book_cover);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Book book = books.get(position);
            Intent intent = new Intent(view.getContext(), EditDeleteBookActivity.class);
            intent.putExtra("BOOK_ID", book.getId());
            view.getContext().startActivity(intent);
        }
    }
}
