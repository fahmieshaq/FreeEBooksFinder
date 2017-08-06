package com.fahmieshaq.freeebooksfinder;

import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class EbookActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Ebook>> {

    // Added LOG_TAG
    private final static String LOG_TAG = EbookActivity.class.getName();

    /**
     * A unique identifier for a loader. The identifier is scoped to a particular LoaderManager instance.
     * Since we plan to have one loader that handles background job, we gave it an ID of 1
     */
    private final int EBOOKS_LOADER_ID = 1;

    // Request URL constants will be constructed into a proper URL that can be used to receive data
    // The maximum number of results to return by default is 10
    private final String ABSOLUTE_URL_REQUEST = "https://www.googleapis.com/books/v1/volumes?q=";
    private final String RELATIVE_URL_REQUEST = "&filter=free-ebooks&orderBy=relevance";

    // mRequestUrl represents request mRequestUrl
    private String mRequestUrl = null;

    // mAdapter is responsible for loading ebook data into list item view
    private EbookAdapter mAdapter;

    // mProgressBar represents the loading indicator
    private ProgressBar mProgressBar;

    // mEmptyState represents the messages that show up when no data is listed
    private TextView mEmptyState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ebook);

        // Hook adapter to list view
        ArrayList<Ebook> ebooks = new ArrayList<>();
        mAdapter = new EbookAdapter(this, ebooks);
        ListView ebooksListView = (ListView) findViewById(R.id.ebooksListView);
        ebooksListView.setAdapter(mAdapter);

        // Check internet connectivity
        if (isInternetConnected()) {

            removeLoadIndicator();
            fillEmptyState(R.string.empty_state_welcome);

            Button findButton = (Button) findViewById(R.id.findButton);
            findButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    EditText editText = (EditText) findViewById(R.id.editText);
                    String searchKeyword = editText.getText().toString();

                    // If user pressed find without providing search keyword, show a red hint
                    if (TextUtils.isEmpty(searchKeyword)) {
                        editText.setHintTextColor(Color.RED);
                    } else {
                        // Clear empty state
                        fillEmptyState(R.string.empty_state_null);

                        // Clear adapter to populate the next search results
                        mAdapter.clear();

                        // Get the URL that will give us data results
                        mRequestUrl = getRequestUrl(searchKeyword);

                        // Launch a new or restart an existent loader that runs http request and handle json responses in the background
                        addLoadIndicator();
                        getSupportLoaderManager().restartLoader(EBOOKS_LOADER_ID, null, EbookActivity.this);
                    }
                }
            });

            // If a saved instance exists, re-use the last created loader.
            if(savedInstanceState != null) {
                getSupportLoaderManager().initLoader(EBOOKS_LOADER_ID, null, EbookActivity.this);
            }
        } else { // Internet is not connected.
            // Since onCreate() runs once, we won't be
            // able to re-execute the code in onCreate() method. Thus, the only way
            // a user can take advantage of the app is wait the internet to get connected
            // and then exit and re-launch the app again. Since monitoring internet
            // connection during app execution was out of scope of this course, I did
            // not solve this problem and made the user aware of the problem as explained
            // in R.string.empty_state_no_internet_first_time
            removeLoadIndicator();
            fillEmptyState(R.string.empty_state_no_internet_first_time);
        }
    }

    /**
     * Embeds seach keyword into the request url
     * @param searchKeyword received from the edit textbox
     * @return a valid request url as string
     */
    private String getRequestUrl(String searchKeyword) {
        searchKeyword = searchKeyword.replace(" ", "+");
        return ABSOLUTE_URL_REQUEST + searchKeyword + RELATIVE_URL_REQUEST;
    }

    /**
     * Check internet connection
     * @return true if internet is live and false if internet is down
     */
    private boolean isInternetConnected() {
        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    /**
     * Remove indeterminate progress bar from main activity when background thread finishes
     */
    private void removeLoadIndicator() {
        mProgressBar = (ProgressBar) findViewById(R.id.indeterminateBar);
        mProgressBar.setVisibility(View.GONE);
    }

    /**
     * Add indeterminate progress bar in main activity when background work in process
     */
    private void addLoadIndicator() {
        mProgressBar = (ProgressBar) findViewById(R.id.indeterminateBar);
        mProgressBar.setVisibility(View.VISIBLE);
    }

    /**
     * Populate empty state textview with the appropriate message
     * @param stringID represents the ID of the string we want to diplay in empty stat textview
     */
    private void fillEmptyState(int stringID) {
        mEmptyState = (TextView) findViewById(R.id.emptyStateTextView);
        mEmptyState.setText(stringID);
    }

    @Override
    public Loader<List<Ebook>> onCreateLoader(int id, Bundle args) {
        /**
         * EbookLoader runs network activities in the background
         * and the results are brought back to onLoadFinished().
         * However, first make sure URL exist. Otherwise, http
         * request connection will fail
         */
        if(mRequestUrl == null) {
            return null;
        }

        return new EbookLoader(this, mRequestUrl);
    }

    @Override
    public void onLoadFinished(Loader<List<Ebook>> loader, List<Ebook> data) {
        // Remove progress indicator and clear list view
        // to display the newly returned results if any
        removeLoadIndicator();
        mAdapter.clear();

        // If data exist, add the data into the adapter which in turns it displays it in List View
        if (data != null && !data.isEmpty()) {
            // Return edit text hint color back to it's default color
            EditText editText = (EditText) findViewById(R.id.editText);
            editText.setHintTextColor(Color.LTGRAY);
            // Clear empty state message
            fillEmptyState(R.string.empty_state_null);
            // Populate list view
            mAdapter.addAll(data);
        } else {
            // If no data is returned, its either because the internet was down in
            // the middle of background work or no best match found
            if (isInternetConnected()) {
                fillEmptyState(R.string.empty_state_no_ebooks);
            } else {
                fillEmptyState(R.string.empty_state_no_internet);
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Ebook>> loader) {
        mAdapter.clear();
    }
}