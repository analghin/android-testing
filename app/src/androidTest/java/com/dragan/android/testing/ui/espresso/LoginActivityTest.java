package com.dragan.android.testing.ui.espresso;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.dragan.android.testing.LoginActivity;
import com.dragan.android.testing.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {

    @Rule
    public ActivityTestRule<LoginActivity> mActivityRule = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void espressoSuccessLogin() {
        Espresso.onView(ViewMatchers.withId(R.id.email)).perform(ViewActions.typeText("email@endava.com"));
        Espresso.onView(ViewMatchers.withId(R.id.password)).perform(ViewActions.click()).perform(ViewActions.typeText("email"));
        Espresso.onView(ViewMatchers.withId(R.id.email_sign_in_button)).perform(ViewActions.click());

        //Verify text from Navigation drawer activity
        Espresso.onView(ViewMatchers.withId(R.id.text)).check(ViewAssertions.matches(ViewMatchers.withText("Welcome!")));
    }
}
