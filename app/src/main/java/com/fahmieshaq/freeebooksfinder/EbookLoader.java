package com.fahmieshaq.freeebooksfinder;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

public class EbookLoader extends AsyncTaskLoader<List<Ebook>> {
    public EbookLoader(Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
    }

    @Override
    public List<Ebook> loadInBackground() {
        
        return null;
    }
}
