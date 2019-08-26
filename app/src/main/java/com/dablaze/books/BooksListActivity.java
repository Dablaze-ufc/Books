package com.dablaze.books;

import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dablaze.books.databinding.ActivityBookDetailBinding;

import java.net.URL;
import java.util.ArrayList;

public class BooksListActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    private ProgressBar fProgressBar;
    private RecyclerView fRecyclerView;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.books_search, menu);
        final MenuItem searchItem = menu.findItem(R.id.books_search_menu);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);
        fProgressBar = findViewById(R.id.progressBar);

        fRecyclerView = findViewById(R.id.recycle_view);
        LinearLayoutManager booksLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        fRecyclerView.setLayoutManager(booksLayoutManager);

        try {
            URL bookUrl = ApiUtils.buildUrl("journals");
            new BookQueryTask().execute(bookUrl);
        } catch (Exception e) {
            Log.d("error", e.getMessage());
        }

    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        try {
            URL bookUrl = ApiUtils.buildUrl(s);
            new BookQueryTask().execute(bookUrl);

        } catch (Exception e) {
            Log.d("error", e.getMessage());

        }

        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        return false;
    }

    public class BookQueryTask extends AsyncTask<URL, Void, String> {

        @Override
        protected String doInBackground(URL... urls) {
            URL searchUrl = urls[0];
            String result = null;
            try {
                result = ApiUtils.getJson(searchUrl);
            } catch (Exception e) {
                Log.d("error", e.getMessage());
            }
            return result;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            fProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String result) {

            TextView errorMsg = findViewById(R.id.text_error);
            fProgressBar.setVisibility(View.GONE);
            if (result == null) {
                errorMsg.setVisibility(View.VISIBLE);
            } else {
                ArrayList<Books> arrayBooks = ApiUtils.getBooksFromJson(result);
                String resultString = "";
                BooksAdapter adapter = new BooksAdapter(arrayBooks);
                fRecyclerView.setAdapter(adapter);


            }

        }


    }
}

