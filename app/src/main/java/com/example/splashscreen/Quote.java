package com.example.splashscreen;

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

public class Quote {
    private URL endpoint;
    private String quote;
    private String attribution;
    private static final String LOGCAT_TAG = "Quotes";
    private static final String REQUEST_METHOD = "GET";
    private String quoteKey = "q";
    private String attributionKey = "a";

    public Quote() {
        try {
            buildURL();
            InputStream inputStream = getInputStream();
            String jsonString = convertStreamToString(inputStream);
            JSONObject json = getJson(jsonString);

            quote = json.getString(quoteKey);
            attribution = json.getString(attributionKey);

        } catch (Exception ex) { // Too many exceptions thrown here FML
            ex.printStackTrace();
            Log.i(LOGCAT_TAG, ">>> " + ex.getMessage());
            setDefaultQuote();
        }
    }

    public String getQuote() {
        return quote;
    }

    public String getAttribution() {
        return attribution;
    }

    // In case the whole thing fails GDI
    private void setDefaultQuote() {
        quote = "Java is to JavaScript what car is to carpet";
        attribution = "Chris Heilmann";
    }

    // Build URL from string
    private void buildURL() throws MalformedURLException {
        String url = "https://zenquotes.io/api/random";
        endpoint = new URL(url);
    }

    // Get HTTP connection from a URL object
    private InputStream getInputStream() throws IOException {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        InputStream inputStream = null;

        try {
            urlConnection = (HttpURLConnection) endpoint.openConnection();
            urlConnection.setRequestMethod(REQUEST_METHOD);
            urlConnection.setDoInput(true);
            urlConnection.connect();

            inputStream = urlConnection.getInputStream();
        } catch (IOException e) {
            throw e;
        }

        return inputStream;
    }

    // Read String returned from InputStream created by a HTTP connection
    private String convertStreamToString(InputStream inputStream) throws IOException {
        BufferedReader reader = null;
        String outString = "";

        StringBuffer buffer = new StringBuffer();
        if (inputStream == null) {
            return null;
        }

        reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;

        while ((line = reader.readLine()) != null) {
            // Add newline for easier debugging
            buffer.append(line + "\n");
        }

        // Empty stream welp
        if (buffer.length() == 0) {
            return null;
        }

        outString = buffer.toString();

        return outString;
    }

    // Get JSON response from the endpoint
    private JSONObject getJson(String jsonString) throws JSONException {
        JSONArray jsonArray = new JSONArray(jsonString);
        JSONObject json = jsonArray.getJSONObject(0);
        return json;
    }

}
