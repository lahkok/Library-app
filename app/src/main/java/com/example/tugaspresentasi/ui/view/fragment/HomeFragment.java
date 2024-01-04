package com.example.tugaspresentasi.ui.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tugaspresentasi.R;
import com.example.tugaspresentasi.data.models.Book;
import com.example.tugaspresentasi.ui.view.AddBookActivity;
import com.example.tugaspresentasi.ui.view.adapter.BookAdapter;
import com.example.tugaspresentasi.ui.viewmodels.AddBookViewModel;
import com.example.tugaspresentasi.ui.viewmodels.BookViewModel;
import com.example.tugaspresentasi.utils.SpacesItemDecoration;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private BookViewModel bookViewModel;
    private RecyclerView recyclerView;
    private BookAdapter bookAdapter;
    private FloatingActionButton fabAddBook;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        fabAddBook = view.findViewById(R.id.fab_add_book);

        fabAddBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddBookActivity.class);
                startActivity(intent);
            }
        });

        recyclerView = view.findViewById(R.id.recyclerView);
        bookViewModel = new ViewModelProvider(this).get(BookViewModel.class);

        bookAdapter = new BookAdapter(new ArrayList<>(), bookViewModel);
        bookAdapter.setMaxItemCount(9);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        recyclerView.setAdapter(bookAdapter);

        int spaceInPixels = 5;
        recyclerView.addItemDecoration(new SpacesItemDecoration(spaceInPixels));

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        int userId = sharedPreferences.getInt("userId", 0);

        List<Book> books = bookViewModel.getBooksByUser(userId);
        Log.d("HomeFragment"," Books fetched:  " + books);
        bookAdapter.setBooks(books);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        int userId = sharedPreferences.getInt("userId", 0);

        bookViewModel.refreshBooks();
        List<Book> books = bookViewModel.getBooksByUser(userId);
        bookAdapter.setBooks(books);
    }
}