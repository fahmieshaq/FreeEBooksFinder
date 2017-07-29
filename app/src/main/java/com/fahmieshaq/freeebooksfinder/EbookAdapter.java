package com.fahmieshaq.freeebooksfinder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class EbookAdapter extends ArrayAdapter<Ebook>{
    public EbookAdapter(Context context, ArrayList<Ebook> ebooks) {
        super(context, 0, ebooks);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        return null;
    }
}
