package com.dablaze.books;

import android.databinding.BindingAdapter;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class Books implements Parcelable {
    private String fId;
    private String fTittle;
    private String fSubTittle;
    private String fAuthors;
    private String fPublishers;
    private String fPublishedDate;
    private String fDescription;
    private String fThumbnail;

    public String getId() {
        return fId;
    }

    public void setId(String id) {
        fId = id;
    }

    public String getTittle() {
        return fTittle;
    }

    public void setTittle(String tittle) {
        fTittle = tittle;
    }

    public String getSubTittle() {
        return fSubTittle;
    }

    public void setSubTittle(String subTittle) {
        fSubTittle = subTittle;
    }

    public String getAuthors() {
        return fAuthors;
    }

    public void setAuthors(String authors) {
        fAuthors = authors;
    }

    public String getPublishers() {
        return fPublishers;
    }

    public void setPublishers(String publishers) {
        fPublishers = publishers;
    }

    public String getPublishedDate() {
        return fPublishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        fPublishedDate = publishedDate;
    }

    public String getDescription() {
        return fDescription;
    }

    public void setDescription(String description) {
        fDescription = description;
    }

    public String getThumbnail() {
        return fThumbnail;
    }

    public void setThumbnail(String thumbnail) {
        fThumbnail = thumbnail;
    }

    public Books(String id, String title, String subTitle, String[] authors, String publisher, String publishedDate, String description, String thumbnail) {
        fId = id;
        fTittle = title;
        fSubTittle = subTitle;
        fAuthors = TextUtils.join(", ", authors);
        fPublishers = publisher;
        fPublishedDate = publishedDate;
        fDescription = description;
        fThumbnail = thumbnail;
    }

    protected Books(Parcel in) {
        fId = in.readString();
        fTittle = in.readString();
        fSubTittle = in.readString();
        fAuthors = in.readString();
        fPublishers = in.readString();
        fPublishedDate = in.readString();
        fDescription = in.readString();
        fThumbnail = in.readString();
    }

    public static final Creator<Books> CREATOR = new Creator<Books>() {
        @Override
        public Books createFromParcel(Parcel in) {
            return new Books(in);
        }

        @Override
        public Books[] newArray(int size) {
            return new Books[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(fId);
        dest.writeString(fTittle);
        dest.writeString(fSubTittle);
        dest.writeString(fAuthors);
        dest.writeString(fPublishers);
        dest.writeString(fPublishedDate);
        dest.writeString(fDescription);
        dest.writeString(fThumbnail);
    }

    @BindingAdapter({"android:imageUrl"})
    public static void loadImage(ImageView view, String imageUrl) {
        Picasso.with(view.getContext()).load(imageUrl).placeholder(R.drawable.book__open).into(view);
    }
}
