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

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import paniuta.trackmywords.R;
import paniuta.trackmywords.beans.Lyrics;
import paniuta.trackmywords.beans.Song;
import paniuta.trackmywords.beans.SongSet;
import paniuta.trackmywords.tasks.GetAsync;

/**
 * The activity where the user can view information about a particular song.
 */
public class LyricsScreen extends ActionBarActivity {

    /**
     * An array of all the TextView ids for reference in the populateView method.
     */
    private int[] viewIds = {R.id.txtSongTitle, R.id.txtArtist, R.id.txtAlbum, R.id.txtLyrics};

    /**
     * An array of string telling which property to fill a particular TextView.
     * Referenced in the populateView method.
     */
    private String[] viewRef = {"title", "artist", "album", "lyrics"};

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
                try {
                    populateView(result);
                }catch(Exception e){
                    Log.e("map error", e.getMessage());
                    buildDialog();
                }
            }
        }).execute(selectedSongId, "lyrics");

    }

    /**
     * Populates all the required text field with their respective values based on the
     * json value returned from the server backend.
     * @param result The string returned after querying the backend.
     * @throws IOException May be thrown if there was an erro mapping the result to string to a Lyric instance.
     */
    private void populateView(String result) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Lyrics songInfo = mapper.readValue(result, Lyrics.class);
        for(int i=0; i<viewIds.length; i++){
            TextView view = (TextView)findViewById(viewIds[i]);
            String appendable = "";
            switch(viewRef[i]){
                case "title":
                    appendable = songInfo.getSongTitle();
                    break;
                case "artist":
                    appendable = songInfo.getArtistName();
                    break;
                case "album":
                    appendable = songInfo.getAlbumTitle();
                    break;
                case "lyrics":
                    appendable = songInfo.getSongLyrics();
                    break;
            }
            view.append(appendable);
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

    /**
     * Creates a dialog to display an error message to the user.
     * The Ok button has an event listener that will call the backToSearchView
     * method to return the user to the previous screen.
     */
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

    /**
     * Used to start a new intent to take the user back to the previous screen.
     * @param view This can be left null when calling the method but needs to be provided so this method can act
     *             as a valid callback for a button click.
     */
    public void backToSearch(View view) {
        Intent getback = new Intent(this, SearchScreen.class);
        startActivity(getback);
    }
}
