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

import java.sql.SQLException;

import paniuta.trackmywords.R;
import paniuta.trackmywords.beans.Song;
import paniuta.trackmywords.beans.SongSet;
import paniuta.trackmywords.cache.Cache;
import paniuta.trackmywords.cache.CacheDAO;
import paniuta.trackmywords.cache.CacheHelper;
import paniuta.trackmywords.tasks.GetAsync;

public class ResultScreen extends ActionBarActivity implements View.OnClickListener {

    /**
     * The key value to be paired up with the user's query string when passing the query string
     * in the intent bundle to the LyricsScreen for further processing.
     */
    public final static String EXTRA_MESSAGE = "com.paniuta.trackmywords.MESSAGE";

    /**
     * Stores the last query string that the user requested.
     */
    private static String lastQueryMessage = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_screen);

        //check to see if the current intent bundle is valid
        boolean valid = validBundle();

        Intent intent = getIntent();
        //Find the text view we will use to display the users search query
        TextView textSearchedFor = (TextView) findViewById(R.id.txtSearchedFor);
        if(valid){
            String searchQuery = intent.getStringExtra(SearchScreen.EXTRA_MESSAGE);
            textSearchedFor.append(searchQuery);
            makeRequest(searchQuery);
        }else{
            textSearchedFor.append(lastQueryMessage);
            try {
                SongSet set = CacheHelper.helpReadCache(this);
                fillView(set);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Makes a request to the backend server using the GetAsync class.
     * The receiver callback will forward the results to the handleResult method.
     * @param searchQuery The search query to be send to the backend to be processed.
     */
    private void makeRequest(String searchQuery){
        new GetAsync(this, new GetAsync.IAsyncReceiver() {
            @Override
            public void onResult(String result) {
                Log.d("song set", result);
                //start populating the list view with the results
                handleResult(result);
            }
        }).execute(searchQuery, "song");
    }

    /**
     * Checks to see if the current bundle is not a null value and contains the SearchScreen.EXTRA_MESSAGE key.
     *
     * @return True if the bundle is not null and contains the EXTRA_MESSAGE key, otherwise returns false.
     */
    private boolean validBundle(){
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle == null || !bundle.containsKey(SearchScreen.EXTRA_MESSAGE)) {
            return false;
        }
        return true;
    }

    /**
     * Maps the result string to a SongSet instance and writes the instance to cache/fills the current
     * view with a list of the song results.
     * @param result The json string returned from the server based on the provided user query.
     */
    private void handleResult(String result){
        ObjectMapper mapper = new ObjectMapper();
        try {
            SongSet set = mapper.readValue(result, SongSet.class);
            fillView(set);
            CacheHelper.helpWriteCache(this, set);
        } catch (Exception e) {
            Log.e("mapper error", e.getMessage());
        }
    }

    /**
     * Fill the LinearLayout view with all the songs from the parsed SongSet instance.
     * @param set The SongSet instance containing all the songs to be displayed to the user.
     */
    private void fillView(SongSet set){
        LinearLayout layout = (LinearLayout) findViewById(R.id.linearLayout);
        for (Song s : set.getSongs()) {
            TextView tv = new TextView(this);
            tv.setMaxLines(3);
            tv.setClickable(true);
            tv.setOnClickListener(this);

            tv.setId(s.getId());
            tv.setText(s.getTitle());
            tv.setPadding(2, 3, 2, 3);

            layout.addView(tv);
        }
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

    @Override
    public void onClick(View v) {
        Log.d("clicked song ID", String.valueOf(v.getId()));
        Intent intent = new Intent(this, LyricsScreen.class);
        String selectedSongId = String.valueOf(v.getId());
        intent.putExtra(EXTRA_MESSAGE, selectedSongId);
        startActivity(intent);
    }
}
