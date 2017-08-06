package com.fahmieshaq.freeebooksfinder;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class EbookAdapter extends ArrayAdapter<Ebook>{

    private static final String LOG_TAG = EbookAdapter.class.getName();

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
         * Find the ebook's object at the given position in the list of ebooks.
         * I added the final keyword because currentEbook is used in an anonymous
         * inner class
         */
        final Ebook currentEbook = getItem(position);

        //******************************* Handle Title ****************************//
        // Populate book title of the current ebook
        TextView titleTextView = (TextView) convertView.findViewById(R.id.list_item_ebook_title);
        // If title is null, display N/A (not available)
        String title = currentEbook.getTitle();
        if(TextUtils.isEmpty(title)) {
            title = getContext().getString(R.string.list_item_ebook_value_not_available);
        }
        titleTextView.setText(title);

        // Open a web browser with the play store Url when a user clicks the book's title
        titleTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(currentEbook.getPlayStoreUrl()));
                if (intent.resolveActivity(getContext().getPackageManager()) != null) {
                    getContext().startActivity(intent);
                }
            }
        });

        //******************************* Handle Category ****************************//
        // Populate categories of the current ebook
        TextView categoryTextView = (TextView) convertView.findViewById(R.id.list_item_ebook_categories);
        // getCategory() returns an array of strings. Concatenate array elements into a comma delimited string
        JSONArray categoriesJsonArray = currentEbook.getCategory();
        // Concatenate array elements with a comma delimeter and assign them into a string
        String categories = null;
        if(categoriesJsonArray != null) {
            try {
                // join() keeps each value with its double quotation. Thus, use replace() to remove the double quotation
                categories = categoriesJsonArray.join(", ").replace("\"", "");
            } catch (JSONException e) {
                Log.e(LOG_TAG, "JSONArray Join() failed", e);
            }
        } else {
            categories = getContext().getString(R.string.list_item_ebook_value_not_available);
        }
        categoryTextView.setText(categories);

        //******************************* Handle Author ****************************//
        // Populate authors of the current ebook
        TextView authorTextView = (TextView) convertView.findViewById(R.id.list_item_ebook_authors);
        // getAuthor() returns an array of strings. Concatenate array elements into a comma delimited string
        JSONArray authorsJsonArray = currentEbook.getAuthor();
        // Concatenate array elements and assign them into a string
        String authors = null;
        if(authorsJsonArray != null) {
            try {
                // join() keeps each value with its double quotation. Thus, use replace() to remove the double quotation
                authors = authorsJsonArray.join(", ").replace("\"", "");
            } catch (JSONException e) {
                Log.e(LOG_TAG, "JSONArray Join() failed", e);
            }
        } else {
            authors = getContext().getString(R.string.list_item_ebook_value_not_available);
        }
        authorTextView.setText(authors);

        //******************************* Handle Published Date ****************************//
        // Populate published date of the current ebook. For simplicity reasons, display date format as is; do not format it.
        TextView publishedDateTextView = (TextView) convertView.findViewById(R.id.list_item_ebook_published_date);
        // If publishedDate is null, display N/A (not available)
        String publishedDate = currentEbook.getPublishedDate();
        if(TextUtils.isEmpty(publishedDate)) {
            publishedDate = getContext().getString(R.string.list_item_ebook_value_not_available);
        }
        publishedDateTextView.setText(publishedDate);

        //******************************* Handle Page Numbers ****************************//
        // Populate number of pages of the current ebook
        TextView pageCountTextView = (TextView) convertView.findViewById(R.id.list_item_ebook_page_count);
        // If pageCount is null, display N/A (not available)
        String pageCount = currentEbook.getPageCount();
        if(TextUtils.isEmpty(pageCount)) {
            pageCount = getContext().getString(R.string.list_item_ebook_value_not_available);
        }
        pageCountTextView.setText(pageCount);

        //******************************* Handle Preview (Google Books) Url ****************************//
        // Populate the preview hyperlink of the current ebook.
        TextView previewTextView = (TextView) convertView.findViewById(R.id.list_item_ebook_preview);
        // The string represents a hyperlink e.g. Available. When user presses a hyperlink, a browser will open with the prospective URL
        CharSequence previewUrl;
        // If preview URL exist, display the word Available. Available is represented as hyperlink
        String getPreviewUrl = currentEbook.getPreviewUrl();
        if(getPreviewUrl != null) {
            // Get the word Available. The word is formatted with an underline. Use getText() to keep the word's format
            previewUrl = getContext().getText(R.string.list_item_ebook_value_available);
            // Set text color to primary dark, so the user would know that the work Available is clickable
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
            // Preview URL doesn't exist, so display N/A message.
            previewUrl = getContext().getString(R.string.list_item_ebook_value_not_available);
        }
        previewTextView.setText(previewUrl);

        //******************************* Handle Play Store Url ****************************//
        // Populate the preview hyperlink of the current ebook.
        TextView playStoreTextView = (TextView) convertView.findViewById(R.id.list_item_ebook_play_store);
        // The string represents a hyperlink e.g. Available. When user presses a hyperlink, a browser will open with the prospective URL
        CharSequence playStoreUrl;
        // If play store URL exists, display the word Available. Available is represented as hyperlink
        String getGoogleBooksUrl = currentEbook.getPlayStoreUrl();
        if(getGoogleBooksUrl != null) {
            // Get the word Available. The word is underlined. Use getText() to keep the word's underline
            playStoreUrl = getContext().getText(R.string.list_item_ebook_value_available);
            // Set text color to primary dark, so the user would know that the work Available is clickable
            playStoreTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimaryDark));
            // The click event opens a web browser with the google books Url
            playStoreTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(currentEbook.getPlayStoreUrl()));
                    if (intent.resolveActivity(getContext().getPackageManager()) != null) {
                        getContext().startActivity(intent);
                    }
                }
            });
        } else {
            // Play store URL doesn't exist. Display N/A message.
            playStoreUrl = getContext().getString(R.string.list_item_ebook_value_not_available);
        }
        playStoreTextView.setText(playStoreUrl);

        //******************************* Handle Language ****************************//
        // Populate language of the current ebook
        TextView languageTextView = (TextView) convertView.findViewById(R.id.list_item_ebook_language);
        // If language is null, display N/A (not available)
        String language = currentEbook.getLanguage();
        if(TextUtils.isEmpty(language)) {
            language = getContext().getString(R.string.list_item_ebook_value_not_available);
        }
        languageTextView.setText(language);

        // Return list item view that is now showing the appropriate data
        return convertView;
    }
}
