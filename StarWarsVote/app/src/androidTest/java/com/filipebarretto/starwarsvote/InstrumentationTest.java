package com.filipebarretto.starwarsvote;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.SmallTest;
import android.widget.TextView;

import com.filipebarretto.starwarsvote.Characters.*;
import com.filipebarretto.starwarsvote.Characters.Character;
import com.filipebarretto.starwarsvote.custom.ToastMatcher;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.not;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@SmallTest
public class InstrumentationTest {

    private CountDownLatch lock = new CountDownLatch(1);
    private List<Character> mCharacters = Character.listAll(Character.class);

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
            MainActivity.class);

    @Test
    public void buttonClickVote() throws Exception {

        for (Character character : mCharacters) {

            lock.await(2000, TimeUnit.MILLISECONDS);

            int result = character.getVotes() + 1;

            onView(allOf(withParent(withId(Character.getCharacterResourceId(character.getName()))), withText(String.valueOf(character.getVotes()))))
                    .check(matches(isDisplayed()));

            onView(allOf(withParent(withId(Character.getCharacterResourceId(character.getName()))), withText(character.getName())))
                    .perform(click());

            onView(allOf(withParent(withId(Character.getCharacterResourceId(character.getName()))), withText(String.valueOf(result))))
                    .check(matches(isDisplayed()));
        }
    }

    @Test
    public void buttonClickToastVote() throws Exception {

        for (Character character : mCharacters) {

            lock.await(2000, TimeUnit.MILLISECONDS);

            onView(allOf(withParent(withId(Character.getCharacterResourceId(character.getName()))), withText(character.getName())))
                    .perform(click());

            onView(withText(character.getQuote())).inRoot(new ToastMatcher())
                    .check(matches(isDisplayed()));

        }

    }


//    @Test
//    public void resetButtonClick() throws Exception {
//
//        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
//
//        onView(withText(R.string.reset))
//                .perform(click());
//
//        for (Character character : mCharacters) {
//
//            onView(allOf(withParent(withId(Character.getCharacterResourceId(character.getName()))), withText("0")))
//                    .check(matches(isDisplayed()));
//        }
//    }


}