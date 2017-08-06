package com.fahmieshaq.freeebooksfinder;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

// This class handle http request and extract data from json results.
public final class QueryUtils {

    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    /**
     * Make http request and then extract data from json results
     * @param urlString represents the URL request which holds the user's search keyword
     * @return a top 10 list of ebooks that best match user's search keyword
     */
    public static final List<Ebook> fetchEbookData(String urlString) {
        URL url = createUrl(urlString);

        String jsonResults = null;
        try {
            jsonResults = makeHttpRequest(url);
        } catch(IOException e) {
            Log.e(LOG_TAG, "Http Request Failed", e);
        }

        List<Ebook> ebooks = extractDataFromJson(jsonResults);

        return ebooks;
    }

    /**
     * Traverse through the returned json results, extract all necessary
     * ebooks data, such as title, and then add the extracted into an
     * ArrayList of ebooks object
     * @param ebooksJson represents json results received from the http request
     * @return a list of ebook objects. Each object holds numbers of data of a single book
     */
    private static final List<Ebook> extractDataFromJson(String ebooksJson) {
        if (TextUtils.isEmpty(ebooksJson)) {
            return null;
        }

        // Create an empty ArrayList that we can start adding ebooks to
        List<Ebook> ebooks = new ArrayList<>();

        try {
            // Traverse through the json string and fetch ebooks necessary data
            // and add them to ebooks array list
            JSONObject baseJsonResponse = new JSONObject(ebooksJson);
            JSONArray itemsJsonArray = baseJsonResponse.optJSONArray("items");

            // items array is where all data belongs. If the array is null,
            // return null. Otherwise, calling itemsJsonArray.length() in the next
            // block will throw java.lang.NullPointerException
            if (itemsJsonArray == null) {
                return null;
            }

            for (int i = 0; i < itemsJsonArray.length(); i++) {
                JSONObject itemJsonObject = itemsJsonArray.optJSONObject(i);

                JSONObject volumeInfoObject = itemJsonObject.optJSONObject("volumeInfo");
                String title = volumeInfoObject.optString("title");
                JSONArray authorsJsonArray = volumeInfoObject.optJSONArray("authors");
                String publishedDate = volumeInfoObject.optString("publishedDate");
                String pageCount = volumeInfoObject.optString("pageCount");
                JSONArray categoriesJsonArray = volumeInfoObject.optJSONArray("categories");

                JSONObject imageLinksObject = volumeInfoObject.optJSONObject("imageLinks");
                String thumbnail = imageLinksObject.optString("thumbnail");
                Uri thumbnailUri = Uri.parse(thumbnail);
                String language = volumeInfoObject.optString("language");
                String previewLink = volumeInfoObject.optString("previewLink");
                String infoLink = volumeInfoObject.optString("infoLink");

                JSONObject accessInfoObject = itemJsonObject.optJSONObject("accessInfo");
                JSONObject pdfObject = accessInfoObject.optJSONObject("pdf");
                String downloadLink = pdfObject.optString("downloadLink");

                ebooks.add(new Ebook(title, categoriesJsonArray, authorsJsonArray, publishedDate, pageCount, previewLink, infoLink, language));
            }
        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the ebooks JSON results", e);
        }

        // Return a list of ebooks
        return ebooks;
    }

    /**
     * This method converts a url request string into a url object
     * @param urlString represents an API url request string
     * @return a URL object of the url request string
     */
    private static final URL createUrl(String urlString) {
        if (TextUtils.isEmpty(urlString)) {
            return null;
        }

        URL url = null;
        try {
            url = new URL(urlString);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Corrupted URL", e);
        }
        return url;
    }

    /**
     * This method connects the url request to the internet and reads the
     * returned stream of bytes into json string
     * @param url represents the request URL
     * @return a json string which holds the necessary data the was requested by the passed URL
     * @throws IOException To run this code inputStream.close(), we had to locally catch or throw IOException.
     */
    private static final String makeHttpRequest(URL url) throws IOException {
        HttpURLConnection connection = null;
        InputStream inputStream = null;
        String jsonResponse = null;
        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(5000);
            connection.setConnectTimeout(5000);
            connection.connect();

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                inputStream = connection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "HTTP failure. Error response code " + connection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Open connection failure", e);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }

            if (inputStream != null) {
                inputStream.close();
            }
        }

        return jsonResponse;
    }

    /**
     * This method interprets stream of bytes into a readable stream of characters i.e. json
     * @param inputStream represents the bytes results of the url request
     * @return a String that represents json response
     */
    private static final String readFromStream(InputStream inputStream) {
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
        BufferedReader reader = new BufferedReader(inputStreamReader);
        StringBuilder output = new StringBuilder();
        try {
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "BufferedReader failure", e);
        }
        return output.toString();
    }
}
