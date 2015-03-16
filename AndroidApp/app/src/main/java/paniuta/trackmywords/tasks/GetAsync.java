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

/**
 * An AsynTask usable to make a request to the backend server and retrieve
 * the json returned from the request.
 */
public class GetAsync extends AsyncTask<String, Void, String> {

    /**
     * A functional interface used as a callback to return the result back to
     * the main activity thread.
     */
    public static interface IAsyncReceiver{
        void onResult(String result);
    }

    /**
     * An instance of the IAsyncReceiver to be used as an event callback.
     */
    private IAsyncReceiver rec;

    /**
     * A progress dialog to tell the user the programming is actively running and trying to
     * load information.
     */
    private ProgressDialog dialog;

    /**
     * The context of the current activity making the request.
     */
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

    /**
     * Converts an InputStream from an HttpResponse entity into a String.
     * @param is The InputStream from the HttpResposne entity
     * @return A String containing all the json data from the response.
     * @throws IOException
     */
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

    /**
     * Takes in the type of query to make and the query parameter, makes a get request,
     * and returns the response as a json string.
     * @param valueIWantToSend Specifies the query parameter for the get request.
     * @param typeOfValue Specifies the type of query type to be making to the backend.
     * @return A string containing the json data returned by the server.
     * @throws IOException
     */
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