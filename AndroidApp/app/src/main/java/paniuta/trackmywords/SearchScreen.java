package paniuta.trackmywords;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;


public class SearchScreen extends ActionBarActivity {

    // for the next activity to query the extra data
    // define the key for the intent's extra using a public constant and app's package name
    // to ensure keys are unique, in case your app interacts with other apps
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

    // adding action to Search button
    public void goSearch(View view) {

        // creating a new activity
        // intent binds activities in runtime
        Intent intent = new Intent(this, ResultScreen.class);

        // get input from textfield and store as String
        EditText textToSearch = (EditText) findViewById(R.id.tfTypeLyrics);
        String message = textToSearch.getText().toString();

        //check if input is not blank and at least 3 characters
        if( message.length() < 3 )
            textToSearch.setError( "Too short!" );

        else {
            // putExtra adds the text value to the intent in key-value pairs
            intent.putExtra(EXTRA_MESSAGE, message);
            startActivity(intent);
            // system receives call and starts an instance of the Activity specified by the Intent object
        }


    }

}
