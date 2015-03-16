package paniuta.trackmywords.screens;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import paniuta.trackmywords.beans.Lyrics;
import paniuta.trackmywords.beans.Song;
import paniuta.trackmywords.beans.SongSet;
import paniuta.trackmywords.tasks.GetAsync;

public class LyricsScreen extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lyrics_screen);

        Intent intent = getIntent();
        String selectedSongId = intent.getStringExtra(ResultScreen.EXTRA_MESSAGE);

        final Context ref = this;
        new GetAsync(this, new GetAsync.IAsyncReceiver() {
            @Override
            public void onResult(String result) {
                Log.d("lyrics result", result);

                ObjectMapper mapper = new ObjectMapper();
                try {
                    Lyrics songInfo = mapper.readValue(result, Lyrics.class);

                    TextView textSongTitle = (TextView) findViewById(R.id.txtSongTitle);
                    textSongTitle.append(songInfo.getSongTitle());
                    Log.d("title", songInfo.getSongTitle());

                    TextView textArtist = (TextView) findViewById(R.id.txtArtist);
                    textArtist.append(songInfo.getArtistName());
                    Log.d("artist", songInfo.getArtistName());

                    TextView textAlbum = (TextView) findViewById(R.id.txtAlbum);
                    textAlbum.append(songInfo.getAlbumTitle());
                    Log.d("artist", songInfo.getAlbumTitle());

                    TextView textLyrics = (TextView) findViewById(R.id.txtLyrics);
                    textLyrics.append(songInfo.getSongLyrics());
                    Log.d("lyrics", songInfo.getSongLyrics());
                }catch(Exception e){
                    Log.e("map error", e.getMessage());
                    buildDialog();
                }
            }
        }).execute(selectedSongId, "lyrics");

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

    private void buildDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(LyricsScreen.this);
        builder.setTitle("Error Displaying Lyrics");
        builder.setMessage("An error occured processing your request and the song information could not be displayed.");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                backToSearch(null);
            }
        });
        builder.create().show();
    }

    public void backToSearch(View view) {
        Intent getback = new Intent(this, SearchScreen.class);
        startActivity(getback);
    }
}
