package com.filipebarretto.starwarsvote;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.filipebarretto.starwarsvote.Characters.Character;
import com.filipebarretto.starwarsvote.Characters.CharacterAdapter;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FirebaseAnalytics mFirebaseAnalytics;

    private GridView mGridview;
    private CharacterAdapter mGridviewAdapter;
    private List<Character> mCharacters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        setupDatabase();

    }

    private void setupDatabase() {
        mCharacters = Character.listAll(Character.class);
        if (mCharacters.size() == 0) {
            createCharacters();
        }

        setupScreen();
    }

    private void setupScreen() {
        mGridview = (GridView) findViewById(R.id.gridview);

        mGridview.setNumColumns(mCharacters.size());

        mGridview.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    return true;
                }
                return false;
            }

        });


        mGridviewAdapter = new CharacterAdapter(this, R.layout.character_item, mCharacters);
        mGridview.setAdapter(mGridviewAdapter);
    }

    private void createCharacters() {
        Character yoda = new Character(getString(R.string.yoda), getString(R.string.yoda_quote), getString(R.string.jedi), 0);
        yoda.save();
        mCharacters.add(yoda);

        Character vader = new Character(getString(R.string.vader), getString(R.string.vader_quote), getString(R.string.sith), 0);
        vader.save();
        mCharacters.add(vader);
    }


    private void reset() {

        for (Character character : mCharacters) {
            character.setVotes(0);
            character.save();
        }

        mGridviewAdapter.clear();
        mCharacters = Character.listAll(Character.class);
        mGridviewAdapter = new CharacterAdapter(this, R.layout.character_item, mCharacters);
        mGridview.setAdapter(mGridviewAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        // OPEN DIALOG WITH INFORMATION ABOUT THE GOALS OF THE APP
        if (id == R.id.action_reset) {

            reset();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
