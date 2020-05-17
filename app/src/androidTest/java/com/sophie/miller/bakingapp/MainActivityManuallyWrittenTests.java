package com.sophie.miller.bakingapp;


import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class MainActivityManuallyWrittenTests {
    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void checkRecyclerViewExists() {
        onView(withId(R.id.activity_main_recycler_view)).check(matches(isDisplayed()));
    }

    @Test
    public void recyclerViewOnClick(){
        onView(withId(R.id.activity_main_recycler_view)).perform(click());
    }

    @Test
    public void recyclerViewNotEmpty(){
        onView(withId(R.id.activity_main_recycler_view)).check(matches(hasMinimumChildCount(1)));
    }

    @Test
    public void clickOpensDetailActivity(){
        onView(withId(R.id.activity_main_recycler_view)).perform(actionOnItemAtPosition(0, click()));
        try {
            Thread.sleep(700);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.activity_detail_container)).check(matches(isDisplayed()));
    }
}
