package com.dablaze.books;

import android.databinding.BindingAdapter;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class Books implements Parcelable {
    public String fId;
    public String fTittle;
    public String fSubTittle;
    public String fAuthors;
    public String fPublishers;
    public String fPublishedDate;
    public String fDescription;
public String fThumbnail;
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
       if(!imageUrl.isEmpty()){
        Picasso.with(view.getContext()).load(imageUrl).placeholder(R.drawable.book__open).into(view);
    }
       else {
           view.setBackgroundResource(R.drawable.book__open);
       }
}
}
