package com.fahmieshaq.freeebooksfinder;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class EbookAdapter extends ArrayAdapter<Ebook>{
    public EbookAdapter(Context context, ArrayList<Ebook> ebooks) {
        super(context, 0, ebooks);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_ebook, parent, false);
        }

        /**
         * Find the ebook object at the given position in the list of ebooks.
         * I added final keyword because currentEbook is used in an anonymous
         * inner class.
         */
        final Ebook currentEbook = getItem(position);

        // Populate book image of the current ebook
        ImageView imageView = (ImageView) convertView.findViewById(R.id.list_item_ebook_image);
        imageView.setImageResource(currentEbook.getImage());

        // Populate book title of the current ebook
        TextView titleTextView = (TextView) convertView.findViewById(R.id.list_item_ebook_title);
        titleTextView.setText(currentEbook.getTitle());
        // Open a web browser with the google books Url when a user clicks the book title
        titleTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(currentEbook.getGoogleBooksUrl()));
                if (intent.resolveActivity(getContext().getPackageManager()) != null) {
                    getContext().startActivity(intent);
                }
            }
        });

        // Populate categories of the current ebook
        TextView categoryTextView = (TextView) convertView.findViewById(R.id.list_item_ebook_categories);
        // getCategory() returns an array of strings. Concatenate array elements into a comma delimited string
        String categories = TextUtils.join(", ", currentEbook.getCategory());
        categoryTextView.setText(categories);

        // Populate authors of the current ebook
        TextView authorTextView = (TextView) convertView.findViewById(R.id.list_item_ebook_authors);
        // getAuthor() returns an array of strings. Concatenate array elements into a comma delimited string
        String authors = TextUtils.join(", ", currentEbook.getAuthor());
        authorTextView.setText(authors);

        // Populate published date of the current ebook. For simplicity reasons, display date format as is; do not format it.
        TextView publishedDateTextView = (TextView) convertView.findViewById(R.id.list_item_ebook_published_date);
        publishedDateTextView.setText(currentEbook.getPublishedDate());

        // Populate number of pages of the current ebook
        TextView pageCountTextView = (TextView) convertView.findViewById(R.id.list_item_ebook_page_count);
        pageCountTextView.setText(currentEbook.getPageCount());

        // Populate the preview hyperlink of the current ebook.
        TextView previewTextView = (TextView) convertView.findViewById(R.id.list_item_ebook_preview);
        // previewUrl holds one of the two strings "Available" or "N/A". The string represents a hyperlink
        CharSequence previewUrl;
        // If preview URL exist, display the word Available. Available is represented as hyperlink
        if(currentEbook.isPreviewUrl()) {
            // Get the word Available. The word is underlined. Use getText() to keep the word's rich content
            previewUrl = getContext().getText(R.string.list_item_ebook_value_available);
            // Set text color to visually convey to the user that the word is clickable
            previewTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimaryDark));
            // The click event opens a web browser with the preview Url
            previewTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(currentEbook.getPreviewUrl()));
                    if (intent.resolveActivity(getContext().getPackageManager()) != null) {
                        getContext().startActivity(intent);
                    }
                }
            });
        } else {
            // Preview URL doesn't exist. Get N/A message.
            previewUrl = getContext().getString(R.string.list_item_ebook_value_not_available);
        }
        previewTextView.setText(previewUrl);

        // Populate the preview hyperlink of the current ebook.
        TextView googleBooksTextView = (TextView) convertView.findViewById(R.id.list_item_ebook_google_books);
        // googleBooksUrl holds one of the two strings "Available" or "N/A". The string represents a hyperlink
        CharSequence googleBooksUrl;
        // If preview URL exist, display the word Available. Available is represented as hyperlink
        if(currentEbook.isGoogleBooksUrl()) {
            // Get the word Available. The word is underlined. Use getText() to keep the word's rich content
            googleBooksUrl = getContext().getText(R.string.list_item_ebook_value_available);
            // Set text color to visually convey to the user that the word Available is clickable
            googleBooksTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimaryDark));
            // The click event opens a web browser with the google books Url
            googleBooksTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(currentEbook.getGoogleBooksUrl()));
                    if (intent.resolveActivity(getContext().getPackageManager()) != null) {
                        getContext().startActivity(intent);
                    }
                }
            });
        } else {
            // Google books URL doesn't exist. Get N/A message.
            googleBooksUrl = getContext().getString(R.string.list_item_ebook_value_not_available);
        }
        googleBooksTextView.setText(googleBooksUrl);


        // Populate the preview hyperlink of the current ebook.
        TextView pdfTextView = (TextView) convertView.findViewById(R.id.list_item_ebook_pdf);
        // pdfUrl holds one of the two strings "Available" or "N/A". The string represents a hyperlink
        CharSequence pdfUrl;
        // If preview URL exist, display the word Available. Available is represented as hyperlink
        if(currentEbook.isPdfUrl()) {
            // Get the word Available. The word is underlined. Use getText() to keep the word's rich content
            pdfUrl = getContext().getText(R.string.list_item_ebook_value_available);
            // Set text color to visually convey to the user that the word Available is clickable
            pdfTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimaryDark));
            // The click event opens a web browser with the google books Url
            pdfTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(currentEbook.getPdfUrl()));
                    if (intent.resolveActivity(getContext().getPackageManager()) != null) {
                        getContext().startActivity(intent);
                    }
                }
            });
        } else {
            // Google books URL doesn't exist. Get N/A message.
            pdfUrl = getContext().getString(R.string.list_item_ebook_value_not_available);
        }
        pdfTextView.setText(pdfUrl);

        // Populate language of the current ebook
        TextView languageTextView = (TextView) convertView.findViewById(R.id.list_item_ebook_language);
        languageTextView.setText(currentEbook.getLanguage());

        // Return list item view that is now showing the appropriate data
        return convertView;
    }
}
