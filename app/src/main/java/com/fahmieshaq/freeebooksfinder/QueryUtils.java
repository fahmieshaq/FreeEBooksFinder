package com.fahmieshaq.freeebooksfinder;

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

public final class QueryUtils {
    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

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

                String language = volumeInfoObject.optString("language");
                String previewLink = volumeInfoObject.optString("previewLink");
                String infoLink = volumeInfoObject.optString("infoLink");

                JSONObject accessInfoObject = itemJsonObject.optJSONObject("accessInfo");
                JSONObject pdfObject = accessInfoObject.optJSONObject("pdf");
                String downloadLink = pdfObject.optString("downloadLink");

                ebooks.add(new Ebook(thumbnail, title, categoriesJsonArray, authorsJsonArray, publishedDate, pageCount, previewLink, infoLink, downloadLink, language));
            }

        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the ebooks JSON results", e);
        }

        // Return a list of ebooks
        return ebooks;
    }

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

    private static final String makeHttpRequest(URL url) throws IOException {
        HttpURLConnection connection = null;
        InputStream inputStream = null;
        String jsonResponse = "";

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
