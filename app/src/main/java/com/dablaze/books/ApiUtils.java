package com.dablaze.books;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class ApiUtils {
    private static final String QUERY_PARAMETER_KEY = "q";
    private static final String KEY = "key";
    private static final String API_KEY = "AIzaSyDrlhz_2R0tDX4GrCJaQgm79j3J3qOe9v8";
    public static final String TITTLE = "intitle:";
    public static final String AUTHOR = "inauthor:";
    public static final String PUBLISHER = "inpublisher:";
    public static final String ISBN = "isbn:";

    private ApiUtils() {
    }

    public static final String BASE_API_URL = "https://www.googleapis.com/books/v1/volumes?";

    public static URL buildUrlFromInput(String tittle, String author, String publisher, String isbn) {
        URL url = null;
        StringBuilder stringBuilder = new StringBuilder();
        if (!tittle.isEmpty()) stringBuilder.append(TITTLE + tittle + "+");
        if (!author.isEmpty()) stringBuilder.append(AUTHOR + author + "+");
        if (!publisher.isEmpty()) stringBuilder.append(PUBLISHER + publisher + "+");
        if (!isbn.isEmpty())
            stringBuilder.append(ISBN + isbn + "+");
        stringBuilder.setLength(stringBuilder.length() - 1);
        String query = stringBuilder.toString();
        Uri uri = Uri.parse(BASE_API_URL).buildUpon()
                .appendQueryParameter(QUERY_PARAMETER_KEY, query)
                .appendQueryParameter(KEY, API_KEY).build();
        try {
            url = new URL(uri.toString());
        } catch (Exception e) {

            e.printStackTrace();
        }
        return url;
    }

    public static URL buildUrl(String tittle) {

        URL url = null;
        Uri uri = Uri.parse(BASE_API_URL).buildUpon()
                .appendQueryParameter(QUERY_PARAMETER_KEY, tittle)
                .appendQueryParameter(KEY, API_KEY).build();
        try {
            url = new URL(uri.toString());


        } catch (Exception e) {
            e.printStackTrace();
        }
        return url;
    }

    public static String getJson(URL url) throws IOException {

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        try {
            InputStream stream = connection.getInputStream();
            Scanner scanner = new Scanner(stream);
            scanner.useDelimiter("\\A");


            boolean hasData = scanner.hasNext();
            if (hasData) {
                return scanner.next();
            } else {
                return null;
            }
        } catch (Exception e) {
            Log.d("Error", e.toString());
            return null;
        } finally {
            connection.disconnect();
        }
    }

    public static ArrayList<Books> getBooksFromJson(String json) {
        final String ID = "id";
        final String TITTLE = "title";
        final String SUBTITLE = "subTitle";
        final String AUTHORS = "authors";
        final String PUBLISHERS = "publisher";
        final String PUBLISHED_DATES = "publishedDate";
        final String ITEMS = "items";
        final String VOLUMEINFO = "volumeInfo";
        final String DESCRIPTION = "description";
        final String IMAGELINKS = "imageLinks";
        final String THUMBNAIL = "thumbnail";
        ArrayList<Books> newBooks = new ArrayList<>();
        try {
            JSONObject jsonBooks = new JSONObject(json);
            JSONArray arrayBooks = jsonBooks.getJSONArray(ITEMS);

            int numberOfBooks = arrayBooks.length();

            for (int i = 0; i < numberOfBooks; i++) {
                JSONObject bookJSON = arrayBooks.getJSONObject(i);
                JSONObject volumeInfoJSON = bookJSON.getJSONObject(VOLUMEINFO);
                JSONObject imageLinksJSON = null;
                if (volumeInfoJSON.has(IMAGELINKS)) {
                    imageLinksJSON = volumeInfoJSON.getJSONObject(IMAGELINKS);
                }


                int authorsNum;
                try {
                    authorsNum = volumeInfoJSON.getJSONArray(AUTHORS).length();
                } catch (Exception e) {
                    authorsNum = 0;
                }
                String[] authors = new String[authorsNum];

                for (int j = 0; j < authorsNum; j++) {
                    authors[j] = volumeInfoJSON.getJSONArray(AUTHORS).getString(j);
                }
                Books book = new Books(bookJSON.getString(ID), volumeInfoJSON.getString(TITTLE),
                        (volumeInfoJSON.isNull(SUBTITLE) ? "" : volumeInfoJSON.getString(SUBTITLE)),
                        authors, (volumeInfoJSON.isNull(PUBLISHERS) ? "" : volumeInfoJSON.getString(PUBLISHERS)),
                        (volumeInfoJSON.isNull(PUBLISHED_DATES) ? "" : volumeInfoJSON.getString(PUBLISHED_DATES)),
                        (volumeInfoJSON.isNull(DESCRIPTION) ? "" : volumeInfoJSON.getString(DESCRIPTION)), (imageLinksJSON == null) ? "" : imageLinksJSON.getString(THUMBNAIL));

                newBooks.add(book);

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return newBooks;

    }
}


