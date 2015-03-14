package paniuta.trackmywords.tasks;

import android.os.AsyncTask;
import android.view.View;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
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
 * Created by Luke on 3/14/2015.
 */
public class PostAsync extends AsyncTask<String, Void, String> {

    public static interface IAsyncReceiver{
        void onResult(String result);
    }

    private IAsyncReceiver rec;

    public PostAsync(IAsyncReceiver rec){
        this.rec = rec;
    }

    @Override
    protected String doInBackground(String... params) {
        // TODO Auto-generated method stub
        String results = "";
        try {
            results = postData(params[0]);
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

    public String postData(String valueIWantToSend) throws IOException {
        HttpClient httpclient = new DefaultHttpClient();
        // specify the URL you want to post to
        HttpPost httppost = new HttpPost("http://somewebsite.com/receiver.php");

        // create a list to store HTTP variables and their values
        List nameValuePairs = new ArrayList();
        // add an HTTP variable and value pair
        nameValuePairs.add(new BasicNameValuePair("myHttpData", valueIWantToSend));
        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
        // send the variable and value, in other words post, to the URL
        HttpResponse response = httpclient.execute(httppost);
        return streamToString(response.getEntity().getContent());
    }
}