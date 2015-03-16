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

    public final static String EXTRA_MESSAGE = "com.paniuta.trackmywords.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_screen);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle == null || !bundle.containsKey(SearchScreen.EXTRA_MESSAGE)) {
            try {
                SongSet set = readCache();
                fillView(set);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return;
        }

        String searchQuery = intent.getStringExtra(SearchScreen.EXTRA_MESSAGE);
        String result = intent.getStringExtra(SearchScreen.EXTRA_MESSAGE2);
        // You searched for:
        TextView textSearchedFor = (TextView) findViewById(R.id.txtSearchedFor);
        // append the text from Search Screen
        try {
            textSearchedFor.append(searchQuery);
        } catch (Exception e) {
            Log.e("null pointer error", e.getMessage());
        }

        //start populating our listview with the results
        handleResult(result);
    }

    private void handleResult(String result){
        ObjectMapper mapper = new ObjectMapper();
        try {
            SongSet set = mapper.readValue(result, SongSet.class);
            if(set.getSongs().size() == 0){

            }
            fillView(set);
            writeCache(set);
        } catch (Exception e) {
            Log.e("mapper error", e.getMessage());
        }
    }

    private void writeCache(SongSet set) throws SQLException {
        if (set.getSongs().size() > 0) {
            CacheDAO cache = Cache.getInstance(this);
            if (!cache.isOpen()) {
                cache.open();
            }
            cache.clear(CacheHelper.RESULT_TABLE_NAME);
            cache.insertSongSet(set);
            cache.close();
        }
    }

    private SongSet readCache() throws SQLException {
        CacheDAO cache = Cache.getInstance(this);
        if(!cache.isOpen()){
            cache.open();
        }
        SongSet set = cache.getResultList();
        cache.close();
        return set;
    }

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
