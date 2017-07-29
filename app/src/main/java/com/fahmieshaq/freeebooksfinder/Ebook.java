package com.fahmieshaq.freeebooksfinder;

public class Ebook {

    private String mTitle;
    private String mCategory;
    private String mAuthor;
    private String mPublishedDate;
    private String mPageCount;
    private int mPreviewUrl;
    private int mGoogleBooksUrl;
    private int mPdfUrl;
    private String mLanguage;

    public Ebook(String title, String category, String author, String publishedDate,
                 String pageCount, int previewUrl, int googleBooksUrl,
                 int pdfUrl, String language) {
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

    public String getTitle() {
        return mTitle;
    }

    public String getCategory() {
        return mCategory;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public String getPublishedDate() {
        return mPublishedDate;
    }

    public String getPageCount() {
        return mPageCount;
    }

    public int getPreviewUrl() {
        return mPreviewUrl;
    }

    public int getGoogleBooksUrl() {
        return mGoogleBooksUrl;
    }

    public int getPdfUrl() {
        return mPdfUrl;
    }

    public String getLanguage() {
        return mLanguage;
    }
}
