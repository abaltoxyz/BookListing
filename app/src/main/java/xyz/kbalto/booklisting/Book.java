package xyz.kbalto.booklisting;

import android.graphics.Bitmap;

/**
 * An {@link Book} object contains information related to a single book.
 */

public class Book {
    private String mTitle;
    private String mAuthor;
    private String mDescription;
    private double mRating;
    private String mThumbnail;
    private String mUrl;
    private Bitmap mBitmap;

    public Book(String Author, String mDescription, double mRating, String mThumbnail, String mTitle, String mUrl, Bitmap mBitmap) {
        this.mAuthor = Author;
        this.mDescription = mDescription;
        this.mRating = mRating;
        this.mThumbnail = mThumbnail;
        this.mTitle = mTitle;
        this.mUrl = mUrl;
        this.mBitmap = mBitmap;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public String getDescription() {
        return mDescription;
    }

    public double getRating() {
        return mRating;
    }

    public String getThumbnail() {
        return mThumbnail;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getUrl() {
        return mUrl;
    }

    public Bitmap getBitmap() {
        return mBitmap;
    }
}
