package com.dragan.android.testing.ui.espresso;

import com.dragan.android.testing.LoginActivity;
import com.dragan.android.testing.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {

    public static final String EMAIL = "foo@example.com";

    public static final String PASSWORD = "hello";

    @Rule
    public ActivityTestRule<LoginActivity> mActivityRule = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void espressoSuccessLogin() {
        Espresso.onView(ViewMatchers.withId(R.id.email)).perform(ViewActions.typeText(EMAIL));
        Espresso.onView(ViewMatchers.withId(R.id.password)).perform(ViewActions.click()).perform(ViewActions.typeText(PASSWORD));
        Espresso.onView(ViewMatchers.withId(R.id.email_sign_in_button)).perform(ViewActions.click());

        //Verify text from Navigation drawer activity
        Espresso.onView(ViewMatchers.withId(R.id.text)).check(ViewAssertions.matches(ViewMatchers.withText("Welcome!")));
    }
}
