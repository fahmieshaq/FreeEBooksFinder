package com.fahmieshaq.freeebooksfinder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class EbookActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ebook);
    }
}


//https://www.googleapis.com/books/v1/volumes?q=flowers&filter=free-ebooks&orderBy=relevance&key=yourAPIKey


//AIzaSyB20aZ9XkAkt4YTPZWDEG71tqPsPJZ0QxM

//Root (object) -> items (array) -> array index 0 is object -> volumeInfo property object
// -> title property -> published date property -> categories propery has an array with one index
// -> imageLinks property (object) -> thumbnail property
// -> preview link property -> info link property (Read Now)
// -> PDF (object) -> downloadLink