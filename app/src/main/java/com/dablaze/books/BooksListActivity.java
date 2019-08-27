package com.dablaze.books;

import android.content.Intent;
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
    public URL fBookUrl;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_advanced_search:
                Intent intent = new Intent(this, SearchActivity.class);
                startActivity(intent);
                return true;

            default:
                int position = item.getItemId() + 1;
                String preferenceName = SpUtil.QUERY + String.valueOf(position);
                String query = SpUtil.getPrefernceString(getApplicationContext(), preferenceName);
                String[] prefPrams = query.split("\\,");
                String[] queryPrams = new String[4];
                for (int i = 0; i<prefPrams.length; i++){
                    queryPrams[i] = prefPrams[i];
                }
                URL bookUrl = ApiUtils.buildUrlFromInput((queryPrams[0]==null) ? "":queryPrams[0],
                    (queryPrams[1]==null) ? "":queryPrams[1],
                    (queryPrams[2]==null) ? "":queryPrams[2],
                    (queryPrams[3]==null) ? "":queryPrams[3]);
                    new BookQueryTask().execute(bookUrl);





                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.books_search, menu);
        final MenuItem searchItem = menu.findItem(R.id.books_search_menu);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(this);
        ArrayList<String> recentList = SpUtil.getQueryList(getApplicationContext());
        int itemNum = recentList.size();
        MenuItem recentMenu;
        for (int i=0; i<itemNum; i++){
            recentMenu = menu.add(Menu.NONE, i, Menu.NONE, recentList.get(i));

        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);
        fProgressBar = findViewById(R.id.progressBar);
        Intent intent = getIntent();
        String query = intent.getStringExtra("QueryUrl");
        try {
            if (query==null){
                fBookUrl = ApiUtils.buildUrl("journals");
            }else {

                    fBookUrl = new URL(query);
                }new BookQueryTask().execute(fBookUrl);
        }
                catch (Exception e){
            Log.d("error", e.getMessage());

        }
        fRecyclerView = findViewById(R.id.recycle_view);
        LinearLayoutManager booksLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        fRecyclerView.setLayoutManager(booksLayoutManager);

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
                BooksAdapter adapter = new BooksAdapter(arrayBooks);
                fRecyclerView.setAdapter(adapter);


            }

        }


    }
}

