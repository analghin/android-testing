package com.dragan.android.testing.ui.espresso;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.dragan.android.testing.LoginActivity;
import com.dragan.android.testing.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
public class EspressoTest {

    @Rule
    public ActivityTestRule<LoginActivity> mActivityRule = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void testNavigationDrawerList() {
        //successful login
        onView(withId(R.id.email)).perform(typeText("email@endava.com"));
        onView(withId(R.id.password)).perform(click()).perform(typeText("email"));
        onView(withId(R.id.email_sign_in_button)).perform(click());

        //open drawer
        onView(withContentDescription("Open navigation drawer")).perform(click());

        onView(withText("Gallery")).perform(click());

        onView(withId(R.id.container)).perform(swipeLeft()).perform(swipeLeft());

        onView(withContentDescription("More options")).perform(click());
        onView(withText("Settings")).perform(click());
        onView(withText("Notifications")).perform(click());

        onView(withText("New message notifications")).perform(click());
        onView(withText("Vibrate")).check(matches(not(isEnabled())));

        onView(withText("New message notifications")).perform(click());
        onView(withText("Vibrate")).check(matches(isEnabled()));

        pressBack();
        pressBack();
        pressBack();

        onView(withId(R.id.text)).check(matches(withText("Welcome!")));
    }
}
