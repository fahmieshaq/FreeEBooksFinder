package com.fahmieshaq.freeebooksfinder;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

public class EbookActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ebook);

        // Category is an array (2nd param) + Authors is an array (3rd Param)
        ArrayList<Ebook> ebooks = new ArrayList<>();
        ebooks.add(new Ebook("Flowers from Hill and Dale", "Flowers",
                "Henrietta Dumont", "1852", "300",
                "http://books.google.ca/books?id=2bCdaZ7KvDsC&printsec=frontcover&dq=flowers&hl=&as_brr=7&cd=2&source=gbs_api",
                "https://play.google.com/store/books/details?id=2bCdaZ7KvDsC&source=gbs_api",
                "http://books.google.ca/books/download/The_Language_of_Flowers.pdf?id=2bCdaZ7KvDsC&hl=&output=pdf&sig=ACfU3U1n9agbciLzFXS4cd_vmKAJsTiEZA&source=gbs_api",
                "en"));
        ebooks.add(new Ebook("Flowers from Hill and Dale 2", "Flowers",
                "Henrietta Dumont", "1852", "300",
                "http://books.google.ca/books?id=2bCdaZ7KvDsC&printsec=frontcover&dq=flowers&hl=&as_brr=7&cd=2&source=gbs_api",
                "https://play.google.com/store/books/details?id=2bCdaZ7KvDsC&source=gbs_api",
                "http://books.google.ca/books/download/The_Language_of_Flowers.pdf?id=2bCdaZ7KvDsC&hl=&output=pdf&sig=ACfU3U1n9agbciLzFXS4cd_vmKAJsTiEZA&source=gbs_api",
                "en"));
    }
}


//https://www.googleapis.com/books/v1/volumes?q=flowers&filter=free-ebooks&orderBy=relevance&key=yourAPIKey


//AIzaSyB20aZ9XkAkt4YTPZWDEG71tqPsPJZ0QxM

//Root (object) -> items (array) -> array index 0 is object -> volumeInfo property object
// -> title property -> published date property -> categories propery has an array with one index
// -> imageLinks property (object) -> thumbnail property
// -> preview link property -> info link property (Read Now)
// -> PDF (object) -> downloadLink