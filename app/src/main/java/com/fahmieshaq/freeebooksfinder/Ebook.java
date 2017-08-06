package com.fahmieshaq.freeebooksfinder;

import android.text.TextUtils;

import org.json.JSONArray;

/**
 * This class holds data returned from API json results.
 */
public class Ebook {

    // mTitle represents book's title
    private String mTitle;
    // mCategory represents a list of categories that a book belongs to
    private JSONArray mCategory;
    // mAuthor represents a list of authors
    private JSONArray mAuthor;
    // mPublishedDate represents the publication date
    private String mPublishedDate;
    // mPageCount represents the book's number of pages
    private String mPageCount;
    // mPreviewUrl represents a URL of Google Books that allows the user to preview the book
    private String mPreviewUrl;
    // mPlayStoreUrl represents a URL of Play Store where the user can read the book
    private String mPlayStoreUrl;
    // mLanguage represents the book's language
    private String mLanguage;

    public Ebook(String title, JSONArray category, JSONArray author, String publishedDate,
                 String pageCount, String previewUrl, String googleBooksUrl, String language) {
        mTitle = title;
        mCategory = category;
        mAuthor = author;
        mPublishedDate = publishedDate;
        mPageCount = pageCount;
        mPreviewUrl = previewUrl;
        mPlayStoreUrl = googleBooksUrl;
        mLanguage = language;
    }

    public String getTitle() {
        if(!TextUtils.isEmpty(mTitle)) {
            return mTitle;
        }

        return null;
    }

    public JSONArray getCategory() {
        if(mCategory != null) {
            return mCategory;
        }
        return null;
    }

    public JSONArray getAuthor() {
        if(mAuthor != null) {
            return mAuthor;
        }
        return null;
    }

    public String getPublishedDate() {
        if(!TextUtils.isEmpty(mPublishedDate)) {
            return mPublishedDate;
        }

        return null;
    }

    public String getPageCount() {
        if(!TextUtils.isEmpty(mPageCount)) {
            return mPageCount;
        }

        return null;
    }

    public String getPreviewUrl() {
        if(!TextUtils.isEmpty(mPreviewUrl)) {
            return mPreviewUrl;
        }
        return null;
    }

    public String getPlayStoreUrl() {
        if(!TextUtils.isEmpty(mPlayStoreUrl)) {
            return mPlayStoreUrl;
        }
        return null;
    }

    public String getLanguage() {
        if(!TextUtils.isEmpty(mLanguage)) {
            return mLanguage;
        }
        return null;
    }
}
