package com.filipebarretto.starwarsvote.Characters;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;

import com.filipebarretto.starwarsvote.R;
import com.orm.SugarRecord;

public class Character extends SugarRecord {

    String name;
    String alliance;
    String quote;
    int votes;

    public Character() {
    }

    public Character(String name, String quote, String alliance, int votes) {
        this.name = name;
        this.alliance = alliance;
        this.quote = quote;
        this.votes = votes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    public String getAlliance() {
        return alliance;
    }

    public void setAlliance(String alliance) {
        this.alliance = alliance;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    public static int getButtonColor(Context context, String alliance) {
        switch (alliance) {
            case "Jedi":
                return ContextCompat.getColor(context, R.color.jedi_color);
            case "Sith":
                return ContextCompat.getColor(context, R.color.sith_color);
            default:
                return Color.BLUE;
        }
    }

    public static int getResourceId(Context context, String pVariableName, String pResourcename, String pPackageName) {
        try {
            return context.getResources().getIdentifier(pVariableName, pResourcename, pPackageName);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static int getCharacterResourceId(String name) {
        switch (name) {
            case "Yoda":
                return R.id.yoda;

            case "Vader":
                return R.id.yoda;

            default:
                return R.id.yoda;
        }
    }
}
