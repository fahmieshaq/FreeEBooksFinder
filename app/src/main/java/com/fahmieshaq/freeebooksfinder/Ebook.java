package com.fahmieshaq.freeebooksfinder;

import android.text.TextUtils;

import org.json.JSONArray;

public class Ebook {

    private String mImageUrl;
    private String mTitle;
    private JSONArray mCategory;
    private JSONArray mAuthor;
    private String mPublishedDate;
    private String mPageCount;
    private String mPreviewUrl;
    private String mGoogleBooksUrl;
    private String mPdfUrl;
    private String mLanguage;

    public Ebook(String imageUrl, String title, JSONArray category, JSONArray author, String publishedDate,
                 String pageCount, String previewUrl, String googleBooksUrl,
                 String pdfUrl, String language) {
        mImageUrl = imageUrl;
        mTitle = title;
        mCategory = category;
        mAuthor = author;
        mPublishedDate = publishedDate;
        mPageCount = pageCount;
        mPreviewUrl = previewUrl;
        mGoogleBooksUrl = googleBooksUrl;
        mPdfUrl = pdfUrl;
        mLanguage = language;
    }

    public String getImage() { return mImageUrl; }

    public String getTitle() {
        return mTitle;
    }

    public JSONArray getCategory() {
        return mCategory;
    }

    public JSONArray getAuthor() {
        return mAuthor;
    }

    public String getPublishedDate() {
        return mPublishedDate;
    }

    public String getPageCount() {
        return mPageCount;
    }

    public String getPreviewUrl() {
        return mPreviewUrl;
    }

    public String getGoogleBooksUrl() {
        return mGoogleBooksUrl;
    }

    public String getPdfUrl() {
        return mPdfUrl;
    }

    public String getLanguage() {
        return mLanguage;
    }

    public boolean isPreviewUrl() {
        boolean isAvailable = false;
        if(!TextUtils.isEmpty(mPreviewUrl)) {
            isAvailable = true;
        }
        return isAvailable;
    }

    public boolean isGoogleBooksUrl() {
        boolean isAvailable = false;
        if(!TextUtils.isEmpty(mGoogleBooksUrl)) {
            isAvailable = true;
        }
        return isAvailable;
    }

    public boolean isPdfUrl() {
        boolean isAvailable = false;
        if(!TextUtils.isEmpty(mPdfUrl)) {
            isAvailable = true;
        }
        return isAvailable;
    }
}
