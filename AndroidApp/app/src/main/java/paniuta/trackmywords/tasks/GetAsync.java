package paniuta.trackmywords.tasks;

import android.net.Uri;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class GetAsync extends AsyncTask<String, Void, String> {

    public static interface IAsyncReceiver{
        void onResult(String result);
    }

    private IAsyncReceiver rec;

    public GetAsync(IAsyncReceiver rec){
        this.rec = rec;
    }

    @Override
    protected String doInBackground(String... params) {
        // TODO Auto-generated method stub
        String results = "";
        try {
            results = getData(params[0], params[1]);
        } catch (IOException e) {
            e.printStackTrace();
            results = "Error During Request: " + e.getMessage();
        }
        return results;
    }

    protected void onPostExecute(String result){
        rec.onResult(result);
    }

    private String streamToString(InputStream is) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder builder = new StringBuilder("");
        String line;
        while((line = reader.readLine()) != null){
            builder.append(line);

        }
        is.close();
        return builder.toString();
    }

    public String getData(String valueIWantToSend, String typeOfValue) throws IOException {
        HttpClient httpclient = new DefaultHttpClient();
        // specify the URL you want to post to

        // create a list to store HTTP variables and their values
        //List nameValuePairs = new ArrayList();
        // add an HTTP variable and value pair
//        nameValuePairs.add(new BasicNameValuePair("query", valueIWantToSend));
//        nameValuePairs.add(new BasicNameValuePair("type", "song"));
        //httpget.setEntity(new UrlEncodedFormEntity(nameValuePairs));
        //String query = URLEncodedUtils.format(nameValuePairs, "utf-8");
        Uri uri = new Uri.Builder().scheme("http").authority("backend-andrewsstuff.rhcloud.com").appendPath("query").appendQueryParameter("type", typeOfValue).appendQueryParameter("query", valueIWantToSend).build();
        HttpGet httpget = new HttpGet(uri.toString());
        // send the variable and value, in other words post, to the URL
        HttpResponse response = httpclient.execute(httpget);
        return streamToString(response.getEntity().getContent());
    }
}