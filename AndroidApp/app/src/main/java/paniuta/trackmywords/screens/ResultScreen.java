package paniuta.trackmywords.screens;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import paniuta.trackmywords.R;
import paniuta.trackmywords.beans.Song;
import paniuta.trackmywords.beans.SongSet;


public class ResultScreen extends ActionBarActivity implements View.OnClickListener {

    public final static String EXTRA_MESSAGE = "com.paniuta.trackmywords.MESSAGE";
    private static final int NUM_OF_TEXTS = 5;
    String songTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_screen);

        Intent intent = getIntent();
        String searchQuery = intent.getStringExtra(SearchScreen.EXTRA_MESSAGE);
        String result = intent.getStringExtra(SearchScreen.EXTRA_MESSAGE2);

        // You searched for:
        TextView textSearchedFor = (TextView) findViewById(R.id.txtSearchedFor);
        // append the text from Search Screen
        textSearchedFor.append(searchQuery);

        ObjectMapper mapper = new ObjectMapper();
        LinearLayout layout = (LinearLayout) findViewById(R.id.linearLayout);

        try {
            SongSet set = mapper.readValue(result, SongSet.class);

            for (Song s:set.getSongs()) {
                TextView tv = new TextView(this);
                tv.setMaxLines(3);
                tv.setClickable(true);
                tv.setOnClickListener(this);

                tv.setId(s.getId());
                tv.setText(s.getTitle());
                layout.addView(tv);

//                textSearchedFor.append(s.getTitle());
//                Log.d("song", s.getTitle());

//                songTitle = set.getSongs().get(0).getTitle();
//                Log.d("song set", songTitle);
            }

        }
        catch(Exception e)
        {
            Log.e("mapper error", e.getMessage());
        }

        // listen to which song title selected then convert to string
        TextView tv1 = (TextView) findViewById(R.id.tv1);
//        TextView tv2 = (TextView) findViewById(R.id.tv2);
//        TextView tv3 = (TextView) findViewById(R.id.tv3);

        // bind listeners
        tv1.setOnClickListener(this);
//        tv2.setOnClickListener(this);
//        tv3.setOnClickListener(this);

    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_result_screen, menu);
//        return true;
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View v) {

        Log.d("clicked", "clicked");
        Intent intent = new Intent(this, LyricsScreen.class);
//        TextView textSongSelected = (TextView)findViewById(R.id.tv1);
        TextView textSongSelected = (TextView)findViewById(v.getId());
//        String message = textSongSelected.getText().toString();


        //gets selected song (3 songs for placeholders)
//        switch(v.getId()){
//            case R.id.tv1:
//                textSongSelected = (TextView) findViewById(R.id.tv1);
//                message = textSongSelected.getText().toString();
//                break;
//            case R.id.tv2:
//                textSongSelected = (TextView) findViewById(R.id.tv2);
//                message = textSongSelected.getText().toString();
//                break;
//            case R.id.tv3:
//                textSongSelected = (TextView) findViewById(R.id.tv3);
//                message = textSongSelected.getText().toString();
//                break;
//        }

        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }
}
