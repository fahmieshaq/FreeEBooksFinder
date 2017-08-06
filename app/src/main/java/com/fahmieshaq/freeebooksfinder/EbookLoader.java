package com.fahmieshaq.freeebooksfinder;

import android.support.v4.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * This loader runs network request and json data extraction in the background
 */
public class EbookLoader extends AsyncTaskLoader<List<Ebook>> {

    private final String mRequestUrl;

    public EbookLoader(Context context, String requestUrl) {
        super(context);
        mRequestUrl = requestUrl;
    }

    @Override
    public List<Ebook> loadInBackground() {
        // Pass API URL request to get a list of ebooks data to be displayed to the user
        return QueryUtils.fetchEbookData(mRequestUrl);
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

}
