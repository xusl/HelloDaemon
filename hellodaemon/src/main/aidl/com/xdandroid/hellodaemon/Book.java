package com.xdandroid.hellodaemon;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by win7 on 2017/9/29.
 */

public class Book implements Parcelable {
    public Book(String name, float price) {
        this.name = name;
        this.price = price;
    }

    protected Book(Parcel in) {
        name = in.readString();
        price = in.readFloat();
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    private float price;

    Book() {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeFloat(price);
    }

    public void readFromParcel(Parcel data)
    {
        this.name = data.readString();
        this.price = data.readFloat();

    }
}
