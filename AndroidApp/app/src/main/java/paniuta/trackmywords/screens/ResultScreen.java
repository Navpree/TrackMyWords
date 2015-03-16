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

import paniuta.trackmywords.R;
import paniuta.trackmywords.beans.Song;
import paniuta.trackmywords.beans.SongSet;


public class ResultScreen extends ActionBarActivity implements View.OnClickListener {

    public final static String EXTRA_MESSAGE = "com.paniuta.trackmywords.MESSAGE";

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
        try {
            textSearchedFor.append(searchQuery);
            //cache operations
        }
        catch(Exception e)
        {
            Log.e("null pointer error", e.getMessage());
        }

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
//                tv.setText(s.getTitle() + "\n"Tyler Shaw - House of Cards);
                tv.setText(s.getTitle());
                tv.setPadding(0, 3, 0, 3);

                layout.addView(tv);

//                songTitle = set.getSongs().get(0).getTitle();
//                Log.d("song set", songTitle);
            }

        }
        catch(Exception e)
        {
            Log.e("mapper error", e.getMessage());
        }

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

        Log.d("clicked song ID", String.valueOf(v.getId()));
        Intent intent = new Intent(this, LyricsScreen.class);
//        TextView textSongSelected = (TextView)findViewById(R.id.tv1);
//        TextView textSongSelected = (TextView)findViewById(v.getId());
//        String message = textSongSelected.getText().toString();
        String selectedSongId = String.valueOf(v.getId());

        intent.putExtra(EXTRA_MESSAGE, selectedSongId);
        startActivity(intent);
    }
}
