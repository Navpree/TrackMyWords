package paniuta.trackmywords.screens;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.fasterxml.jackson.databind.ObjectMapper;

import paniuta.trackmywords.tasks.GetAsync;
import paniuta.trackmywords.R;
import paniuta.trackmywords.beans.SongSet;


public class SearchScreen extends ActionBarActivity {

    // for the next activity to query the extra data
    // define the key for the intent's extra using a public constant and app's package name
    // to ensure keys are unique, in case your app interacts with other apps
    public final static String EXTRA_MESSAGE = "com.paniuta.trackmywords.MESSAGE";
    public final static String EXTRA_MESSAGE2 = "com.paniuta.trackmywords.MESSAGE2";
    Intent intent;
    String searchQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_screen);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search_screen, menu);
        return true;
    }

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

    // adding action to Search button
    public void goSearch(View view) {

        // creating a new activity
        // intent binds activities in runtime
        intent = new Intent(this, ResultScreen.class);

        // get input from textfield and store as String
        EditText textToSearch = (EditText) findViewById(R.id.tfTypeLyrics);
        searchQuery = textToSearch.getText().toString();

        //check if input is not blank and at least 3 characters
        if( searchQuery.length() < 3 )
            textToSearch.setError( "Too short!" );

        else {
            // putExtra adds the text value to the intent in key-value pairs
            //intent.putExtra(EXTRA_MESSAGE, message);
            //startActivity(intent);
            new GetAsync(new GetAsync.IAsyncReceiver() {
                @Override
                public void onResult(String result) {
                    Log.d("log", result);
                    ObjectMapper mapper = new ObjectMapper();
                    try {
                        SongSet set = mapper.readValue(result, SongSet.class);
                        String songTitle = set.getSongs().get(0).getTitle();
                        //intent.putExtra(EXTRA_MESSAGE2, songTitle);

                        Log.d("song set", result);
                        intent.putExtra(EXTRA_MESSAGE, searchQuery);
                        intent.putExtra(EXTRA_MESSAGE2, result);
                        startActivity(intent);
                    }
                    catch(Exception e)
                    {
                        Log.e("mapper error", e.getMessage());
                    }
                }
            }).execute(searchQuery, "song");
            // system receives call and starts an instance of the Activity specified by the Intent object
        }


    }

}