package com.filipebarretto.starwarsvote.Characters;

import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.filipebarretto.starwarsvote.R;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.List;


public class CharacterAdapter extends ArrayAdapter<Character> {

    private FirebaseAnalytics mFirebaseAnalytics;

    private Context mContext;
    private List<Character> mCharacters;

    private Toast mToast;

    public CharacterAdapter(Context context, int textViewResourceId, List<Character> characters) {
        super(context, textViewResourceId, characters);
        mContext = context;
        mCharacters = characters;

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(mContext);
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View view, ViewGroup parent) {

        final Character character = mCharacters.get(position);

        if (view == null) {

            LayoutInflater vi;
            vi = LayoutInflater.from(mContext);
            view = vi.inflate(R.layout.character_item, null);

            view.setId(Character.getCharacterResourceId(character.getName()));

            int statusBarHeight = 0;
            int resource = mContext.getResources().getIdentifier("status_bar_height", "dimen", "android");
            if (resource > 0) {
                statusBarHeight = mContext.getResources().getDimensionPixelSize(resource);
            }

            int navigationBarHeight = 0;
            int resourceId = mContext.getResources().getIdentifier("navigation_bar_height", "dimen", "android");
            if (resourceId > 0) {
                navigationBarHeight = mContext.getResources().getDimensionPixelSize(resourceId);
            }

            DisplayMetrics metrics = mContext.getResources().getDisplayMetrics();
            view.setMinimumHeight(metrics.heightPixels - ( navigationBarHeight + statusBarHeight));

            final ImageView mCharacterImage = (ImageView) view.findViewById(R.id.characterImage);
            final TextView mCharacterVotes = (TextView) view.findViewById(R.id.characterVotes);
            final Button mCharacterButton = (Button) view.findViewById(R.id.characterButton);

            mCharacterImage.setImageResource(Character.getResourceId(mContext, character.getName().toLowerCase(), "drawable", mContext.getPackageName()));

            mCharacterVotes.setText(String.valueOf(character.getVotes()));

            mCharacterButton.setText(character.getName());
            mCharacterButton.setBackgroundColor(Character.getButtonColor(mContext, character.getAlliance()));

            mCharacterButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    customEvent(character.getName(), character.getQuote());
                    showToast(character.getQuote());
                    increment(character, mCharacterVotes);
                }
            });


        }

        return view;
    }

    private void showToast(String quote) {

        if (mToast != null) {
            mToast.cancel();
        }
        mToast = Toast.makeText(mContext, quote, Toast.LENGTH_SHORT);
        mToast.setGravity(Gravity.CENTER, 0, 0);
        mToast.show();
    }

    private void customEvent(String id, String name) {
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, id);
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, name);
        mFirebaseAnalytics.logEvent("click", bundle);
    }

    private void increment(Character character, TextView mTextView) {
        character.setVotes(character.getVotes() + 1);
        character.save();
        mTextView.setText(String.valueOf(character.getVotes()));
    }


}
