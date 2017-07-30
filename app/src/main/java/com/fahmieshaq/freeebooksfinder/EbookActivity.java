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

        // Category is an array (2nd param) + Authors is an array (3rd Param)
        ArrayList<Ebook> ebooks = new ArrayList<>();
        String[] val = {"Flowers", "House"};
        ebooks.add(new Ebook(R.drawable.sample_image, "Flowers from Hill and Dale", val,
                val, "1852", "300",
                "https://play.google.com/store/books/details?id=pK9YAAAAMAAJ&source=gbs_api",
                null,
                "http://www.fahmieshaq.com",
                "en"));
        ebooks.add(new Ebook(R.drawable.sample_image, "Flowers from Hill and Dale 2", val,
                val, "1985", "300",
                "",
                "https://play.google.com/store/books/details?id=pK9YAAAAMAAJ&source=gbs_api",
                "http://books.google.ca/books/download/Flowers_from_Hill_and_Dale.pdf?id=pK9YAAAAMAAJ&hl=&output=pdf&sig=ACfU3U0UH-bXyjbr2c_a7a3KgfVGtHUkQg&source=gbs_api",
                "en"));

        EbookAdapter adapter = new EbookAdapter(this, ebooks);
        ListView ebooksListView = (ListView) findViewById(R.id.ebooksListView);
        ebooksListView.setAdapter(adapter);
    }
}


//https://www.googleapis.com/books/v1/volumes?q=flowers&filter=free-ebooks&orderBy=relevance&key=yourAPIKey


//AIzaSyB20aZ9XkAkt4YTPZWDEG71tqPsPJZ0QxM

//Root (object) -> items (array) -> array index 0 is object -> volumeInfo property object
// -> title property -> published date property -> categories propery has an array with one index
// -> imageLinks property (object) -> thumbnail property
// -> preview link property -> info link property (Read Now)
// -> PDF (object) -> downloadLink