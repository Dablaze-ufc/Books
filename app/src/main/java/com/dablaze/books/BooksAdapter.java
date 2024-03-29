package com.dablaze.books;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.BooksViewHolder> {
    ArrayList<Books> fBooksArrayList;
    public BooksAdapter(ArrayList<Books> booksArrayList) {
        this.fBooksArrayList = booksArrayList;
    }
    @NonNull
    @Override
    public BooksViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        View itemView = LayoutInflater.from(context).inflate(R.layout.books_layout, viewGroup, false);
        return new BooksViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(@NonNull BooksViewHolder booksViewHolder, int position) {
        Books book = fBooksArrayList.get(position);
        booksViewHolder.bind(book);
    }
    @Override
    public int getItemCount() {
        return fBooksArrayList.size();
    }
    public class BooksViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView fTittleR;
        TextView fAuthorsR;
        TextView fPublisdedDateR;
        TextView fPublishersR;
        ImageView fImageViewR;
        public BooksViewHolder(@NonNull View itemView) {
            super(itemView);
            fImageViewR = itemView.findViewById(R.id.imageView_recycle);
            fTittleR = itemView.findViewById(R.id.text_tittle);
            fAuthorsR = itemView.findViewById(R.id.text_authors);
            fPublisdedDateR = itemView.findViewById(R.id.text_publishedDate);
            fPublishersR = itemView.findViewById(R.id.text_publisher);
            itemView.setOnClickListener(this);
        }
        public void bind(Books book) {
            fTittleR.setText(book.fTittle);
            fAuthorsR.setText(book.fAuthors);
            fPublishersR.setText(book.fPublishers);
            fPublisdedDateR.setText(book.fPublishedDate);
            Books.loadImage(fImageViewR, book.fThumbnail);
        }
        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Books selectedBook = fBooksArrayList.get(position);
            Intent intent = new Intent(v.getContext(), BookDetail.class);
            intent.putExtra("Book", selectedBook);
            v.getContext().startActivity(intent);
        }
    }
}

