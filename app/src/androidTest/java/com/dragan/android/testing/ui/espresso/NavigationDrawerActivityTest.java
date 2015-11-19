package com.dragan.android.testing.ui.espresso;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.dragan.android.testing.NavigationDrawerActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class NavigationDrawerActivityTest {

    @Rule
    public ActivityTestRule<NavigationDrawerActivity> mActivityRule = new ActivityTestRule<>(NavigationDrawerActivity.class);

    @Test
    public void testNavigationDrawerList() {
        onView(withContentDescription("Open navigation drawer")).perform(click());

        onView(withText("Import")).check(matches(isDisplayed()));
        onView(withText("Gallery")).check(matches(isDisplayed()));
        onView(withText("Slideshow")).check(matches(isDisplayed()));
        onView(withText("Tools")).check(matches(isDisplayed()));
        onView(withText("Share")).check(matches(isDisplayed()));
        onView(withText("Send")).check(matches(isDisplayed()));
    }
}
