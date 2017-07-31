package com.fahmieshaq.freeebooksfinder;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class EbookActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Ebook>>{

    private final static String LOG_TAG = EbookActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ebook);

        ArrayList<Ebook> ebooks = new ArrayList<>();

        EbookAdapter adapter = new EbookAdapter(this, ebooks);
        ListView ebooksListView = (ListView) findViewById(R.id.ebooksListView);
        ebooksListView.setAdapter(adapter);

    }

    @Override
    public Loader<List<Ebook>> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<List<Ebook>> loader, List<Ebook> data) {

    }

    @Override
    public void onLoaderReset(Loader<List<Ebook>> loader) {

    }
}