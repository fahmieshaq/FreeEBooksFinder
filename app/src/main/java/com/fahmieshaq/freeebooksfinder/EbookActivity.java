package com.fahmieshaq.freeebooksfinder;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;

public class EbookActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ebook);

        ArrayList<Ebook> ebooks = new ArrayList<>();

        String[] categories = {"Flowers", "House"};
        String[] authors = {"Frances Sargent Osgood"};
        ebooks.add(new Ebook(R.drawable.sample_img_1,
                "A wreath of wild flowers from New England",
                categories,
                authors,
                "1838",
                "132",
                "http://books.google.ca/books?id=dGEEAAAAQAAJ&printsec=frontcover&dq=flowers&hl=&as_brr=7&cd=6&source=gbs_api",
                "https://play.google.com/store/books/details?id=dGEEAAAAQAAJ&source=gbs_api",
                "http://books.google.ca/books/download/A_wreath_of_wild_flowers_from_New_Englan.pdf?id=dGEEAAAAQAAJ&hl=&output=pdf&sig=ACfU3U0vW4YcA2-faT0ZwcPAD-jN-MLpJw&source=gbs_api",
                "en"));

        categories = new String[] {"Flowers"};
        authors = new String[] {"Anne Pratt"};
        ebooks.add(new Ebook(R.drawable.sample_img_2,
                "Flowers and their associations",
                categories,
                authors,
                "1840",
                null,
                "http://books.google.ca/books?id=ktkDAAAAQAAJ&printsec=frontcover&dq=flowers&hl=&as_brr=7&cd=3&source=gbs_api",
                "https://play.google.com/store/books/details?id=ktkDAAAAQAAJ&source=gbs_api",
                "http://books.google.ca/books/download/Flowers_and_their_associations.pdf?id=ktkDAAAAQAAJ&hl=&output=pdf&sig=ACfU3U3t31n5hoTWLkxSLsAOK6I3ZhgdHw&source=gbs_api",
                "en"));

        EbookAdapter adapter = new EbookAdapter(this, ebooks);
        ListView ebooksListView = (ListView) findViewById(R.id.ebooksListView);
        ebooksListView.setAdapter(adapter);
    }
}