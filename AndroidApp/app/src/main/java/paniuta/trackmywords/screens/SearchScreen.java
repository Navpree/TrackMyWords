package paniuta.trackmywords.screens;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import paniuta.trackmywords.tasks.GetAsync;
import paniuta.trackmywords.R;

/**
 * The activity for the main search screen where the user can type in their search query and tap the search button
 */
public class SearchScreen extends ActionBarActivity {

    //unique keys for the message that will be placed in the intent bundle
    public final static String EXTRA_MESSAGE = "com.paniuta.trackmywords.MESSAGE";

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

    /**
     * Starts a new activity intent for the ResultScreen, adding in the query string to the intent bundle.
     * @param view The view passed in during the button click call
     */
    public void goSearch(View view) {
        // get input from textfield and store as String sear
        EditText textToSearch = (EditText) findViewById(R.id.tfTypeLyrics);
        String searchQuery = textToSearch.getText().toString();

        //check if input is not blank and at least 3 characters ignoring leading spaces
        if( searchQuery.trim().length() < 3 ) {
            textToSearch.setError("Too shot, search term must be at least 3 characters in length.");
        }else {
            //create a new intent to start the result activity
            Intent intent = new Intent(SearchScreen.this, ResultScreen.class);
            //add the search query to the intent bundle
            intent.putExtra(EXTRA_MESSAGE, searchQuery);
            startActivity(intent);
        }
    }
}