package paniuta.trackmywords;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;


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


        // get song selected then convert to string
        TextView tv1 = (TextView) findViewById(R.id.tv1);
        TextView tv2 = (TextView) findViewById(R.id.tv2);
        TextView tv3 = (TextView) findViewById(R.id.tv3);

        // bind listeners
        tv1.setOnClickListener(this);
        tv2.setOnClickListener(this);
        tv3.setOnClickListener(this);


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

        Intent intent = new Intent(this, LyricsScreen.class);
        String message = "";
        TextView textSongSelected;

        switch(v.getId()){
            case R.id.tv1:
                textSongSelected = (TextView) findViewById(R.id.tv1);
                message = textSongSelected.getText().toString();
                break;
            case R.id.tv2:
                textSongSelected = (TextView) findViewById(R.id.tv1);
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
