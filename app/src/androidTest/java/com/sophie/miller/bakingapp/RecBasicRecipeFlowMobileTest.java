package com.sophie.miller.bakingapp;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class RecBasicRecipeFlowMobileTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void mainRecipeFlow() {
        ViewInteraction cardView = onView(
                allOf(withId(R.id.item_recipe_card_CV),
                        childAtPosition(
                                allOf(withId(R.id.activity_main_recycler_view),
                                        childAtPosition(
                                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                                0)),
                                0),
                        isDisplayed()));
        cardView.perform(click());
        try {
            Thread.sleep(700);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ViewInteraction cardView2 = onView(
                allOf(withId(R.id.item_recipe_card_CV),
                        childAtPosition(
                                allOf(withId(R.id.fragment_steps_recycler_view),
                                        childAtPosition(
                                                withId(R.id.activity_detail_container),
                                                0)),
                                0),
                        isDisplayed()));
        cardView2.perform(click());

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.fragment_step_details_next_BTN), withText("next"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.activity_detail_container),
                                        0),
                                3),
                        isDisplayed()));
        appCompatButton.perform(click());
        try {
            Thread.sleep(700);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.fragment_step_details_next_BTN), withText("next"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.activity_detail_container),
                                        0),
                                3),
                        isDisplayed()));
        appCompatButton2.perform(click());
        try {
            Thread.sleep(700);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.fragment_step_details_next_BTN), withText("next"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.activity_detail_container),
                                        0),
                                3),
                        isDisplayed()));
        appCompatButton3.perform(click());
        try {
            Thread.sleep(700);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ViewInteraction appCompatButton4 = onView(
                allOf(withId(R.id.fragment_step_details_next_BTN), withText("next"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.activity_detail_container),
                                        0),
                                3),
                        isDisplayed()));
        appCompatButton4.perform(click());
        try {
            Thread.sleep(700);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ViewInteraction appCompatButton5 = onView(
                allOf(withId(R.id.fragment_step_details_next_BTN), withText("next"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.activity_detail_container),
                                        0),
                                3),
                        isDisplayed()));
        appCompatButton5.perform(click());
        try {
            Thread.sleep(700);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ViewInteraction appCompatButton6 = onView(
                allOf(withId(R.id.fragment_step_details_next_BTN), withText("next"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.activity_detail_container),
                                        0),
                                3),
                        isDisplayed()));
        appCompatButton6.perform(click());
        try {
            Thread.sleep(700);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ViewInteraction appCompatButton7 = onView(
                allOf(withId(R.id.fragment_step_details_next_BTN), withText("next"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.activity_detail_container),
                                        0),
                                3),
                        isDisplayed()));
        appCompatButton7.perform(click());
        try {
            Thread.sleep(700);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ViewInteraction appCompatButton8 = onView(
                allOf(withId(R.id.fragment_step_details_next_BTN), withText("next"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.activity_detail_container),
                                        0),
                                3),
                        isDisplayed()));
        appCompatButton8.perform(click());
        try {
            Thread.sleep(700);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ViewInteraction appCompatButton9 = onView(
                allOf(withId(R.id.fragment_step_details_back_BTN), withText("back"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.activity_detail_container),
                                        0),
                                2),
                        isDisplayed()));
        appCompatButton9.perform(click());
        try {
            Thread.sleep(700);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ViewInteraction appCompatButton10 = onView(
                allOf(withId(R.id.fragment_step_details_next_BTN), withText("next"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.activity_detail_container),
                                        0),
                                3),
                        isDisplayed()));
        appCompatButton10.perform(click());
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
