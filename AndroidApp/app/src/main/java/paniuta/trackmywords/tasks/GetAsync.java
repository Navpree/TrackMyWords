package paniuta.trackmywords.tasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
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
    private ProgressDialog dialog;
    private Context context;

    public GetAsync(Context context, IAsyncReceiver rec){
        this.rec = rec;
        this.context = context;
    }

    @Override
    protected void onPreExecute(){
        Log.d("GetAsync Debug", "Starting onPreExecute");
        dialog = ProgressDialog.show(context, "Loading", "Loading results, please wait...", true, false);
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {
        String results;
        try {
            results = getData(params[0], params[1]);
        } catch (IOException e) {
            e.printStackTrace();
            results = "Error During Request: " + e.getMessage();
        }
        return results;
    }

    @Override
    protected void onPostExecute(String result){
        Log.d("GetAsync Debug", "Starting onPostExecute");
        if(dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
        Log.d("GetAsync Debug", "Returning results");
        rec.onResult(result);
        super.onPostExecute(result);
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
        //create an encoded uri that we want to send our request to
        Uri uri = new Uri.Builder().scheme("http").authority("backend-andrewsstuff.rhcloud.com")
                .appendPath("query")
                .appendQueryParameter("type", typeOfValue)
                .appendQueryParameter("query", valueIWantToSend)
                .build();
        HttpGet httpget = new HttpGet(uri.toString());
        // send the variable and value, in other words post, to the URL
        HttpResponse response = httpclient.execute(httpget);
        return streamToString(response.getEntity().getContent());
    }
}