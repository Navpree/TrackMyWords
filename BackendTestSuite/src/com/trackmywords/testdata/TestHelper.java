package com.trackmywords.testdata;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class TestHelper {

    public static HttpURLConnection createConnection(String url) throws IOException {
        URL location = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) location.openConnection();
        connection.setDoInput(true);
        connection.setDoOutput(true);
        connection.connect();
        return connection;
    }

    public static String getResponseString(HttpURLConnection connection) throws IOException {
        InputStream in;
        if (connection.getResponseCode() == 200) {
            in = connection.getInputStream();
        } else {
            in = connection.getErrorStream();
        }
        StringBuffer buffer = new StringBuffer("");
        String line;
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
        }
        return buffer.toString();
    }

}
