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
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class DetailActivityManuallyWrittenTests {

    @Rule
    public ActivityTestRule<DetailActivity> mainActivityActivityTestRule = new ActivityTestRule<>(DetailActivity.class);

    @Test
    public void checkStepsRecyclerViewExists() {
        onView(withId(R.id.fragment_steps_recycler_view)).check(matches(isDisplayed()));
    }

    @Test
    public void stepClick() {
        onView(withId(R.id.fragment_steps_recycler_view)).perform(actionOnItemAtPosition(0, click()));

    }

    @Test
    public void viewIngredients() {
        onView(withId(R.id.fragment_steps_recycler_view)).perform(actionOnItemAtPosition(0, click()));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.fragment_step_details_ingredients_RV)).check(matches(isDisplayed()));
    }

    @Test
    public void viewStep() {
        onView(withId(R.id.fragment_steps_recycler_view)).perform(actionOnItemAtPosition(1, click()));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.fragment_step_details_player)).check(matches(isDisplayed()));
    }

}
