package com.dablaze.books;

import android.database.DatabaseUtils;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.dablaze.books.databinding.ActivityBookDetailBinding;

public class BookDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);
        TextView textView = findViewById(R.id.text_description);
        Books book = getIntent().getParcelableExtra("Book");
        ActivityBookDetailBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_book_detail);
        binding.setBooks(book);
    }
}
