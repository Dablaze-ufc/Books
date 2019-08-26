package com.dablaze.books;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.net.URL;

public class  SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        final EditText tittle = findViewById(R.id.search_tittle);
        final EditText author = findViewById(R.id.search_author);
        final EditText publiser = findViewById(R.id.search_publisher);
        final EditText isbn = findViewById(R.id.search_isbn);
        final Button searchButton = findViewById(R.id.button_search);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String gTitle = tittle.getText().toString().trim();
                String gAuthor = author.getText().toString().trim();
                String gPublisher = publiser.getText().toString().trim();
                String gISBN = isbn.getText().toString().trim();
                if(gTitle.isEmpty() && gAuthor.isEmpty() && gPublisher.isEmpty() && gISBN.isEmpty()){
                    String message = getString(R.string.no_input_message);
                    Toast.makeText(getApplicationContext(),message, Toast.LENGTH_LONG).show();
                }else {
                    URL queryURL = ApiUtils.buildUrlFromInput(gTitle, gAuthor, gPublisher, gISBN);
                    Intent intent = new Intent(getApplicationContext(),BooksListActivity.class);
                    intent.putExtra("QueryUrl",queryURL.toString());
                    startActivity(intent);
                }

            }
        });
    }
}
