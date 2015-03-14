package paniuta.trackmywords;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.sql.SQLException;
import java.util.List;

import paniuta.trackmywords.cache.Cache;
import paniuta.trackmywords.cache.CacheDAO;
import paniuta.trackmywords.cache.CacheHelper;
import paniuta.trackmywords.cache.Result;

public class ResultScreen extends ActionBarActivity implements View.OnClickListener {

    public final static String EXTRA_MESSAGE = "com.paniuta.trackmywords.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_screen);

        Intent intent = getIntent();
        String message = intent.getStringExtra(SearchScreen.EXTRA_MESSAGE);

        // append the text from Search Screen
        TextView textSearchedFor = (TextView) findViewById(R.id.txtSearchedFor);
        textSearchedFor.append(message);


        // listen to which song title selected then convert to string
        TextView tv1 = (TextView) findViewById(R.id.tv1);
        TextView tv2 = (TextView) findViewById(R.id.tv2);
        TextView tv3 = (TextView) findViewById(R.id.tv3);

        // bind listeners
        tv1.setOnClickListener(this);
        tv2.setOnClickListener(this);
        tv3.setOnClickListener(this);

        if(!message.equals("RETURN_FROM_LYRIC_RESULTS")){
            getResults(message);
        }else{
            readCache();
        }
    }

    private void getResults(String songTitle) {
        //TODO get results from server, write results to cache
    }

    private void readCache(){
        CacheDAO cache = Cache.getInstance(this);
        try{
            if(!cache.isOpen()){
                cache.open();
            }
            List<Result> results = cache.getResultList();
            for(Result r : results){
                //TODO: add results to activity list
            }
            cache.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
        cache = null;
    }

    private void writeToCache(List<Result> results){
        CacheDAO cache = Cache.getInstance(this);
        try {
            if (!cache.isOpen()) {
                cache.open();
            }
            cache.clear(CacheHelper.RESULT_TABLE_NAME);
            for(Result r : results){
                cache.insert(r);
            }
            cache.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        cache = null;
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

        Intent intent = new Intent(this, LyricsScreen.class);
        String message = "";
        TextView textSongSelected;

        //gets selected song (3 songs for placeholders)
        switch(v.getId()){
            case R.id.tv1:
                textSongSelected = (TextView) findViewById(R.id.tv1);
                message = textSongSelected.getText().toString();
                break;
            case R.id.tv2:
                textSongSelected = (TextView) findViewById(R.id.tv2);
                message = textSongSelected.getText().toString();
                break;
            case R.id.tv3:
                textSongSelected = (TextView) findViewById(R.id.tv3);
                message = textSongSelected.getText().toString();
                break;
        }

        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }
}
