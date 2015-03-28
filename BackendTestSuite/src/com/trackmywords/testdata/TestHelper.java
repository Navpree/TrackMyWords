package com.trackmywords.testdata;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

public class TestHelper {

    public static HttpURLConnection createConnection(String url, String method) throws IOException {
        URL location = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) location.openConnection();
        connection.setRequestMethod(method);
        connection.setDoInput(true);
        connection.setDoOutput(true);
        connection.connect();
        return connection;
    }

    public static HttpURLConnection createConnection(String url) throws IOException {
        return TestHelper.createConnection(url, "GET");
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
        if(in == null){
            return "";
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
        }
        return buffer.toString();
    }

    public static void writePostParameters(HttpURLConnection con, String params) throws IOException {
        try(DataOutputStream out = new DataOutputStream(con.getOutputStream())){
            out.write(params.getBytes(Charset.forName("UTF-8")));
        }catch(Exception e){
            throw e;
        }
    }

}
