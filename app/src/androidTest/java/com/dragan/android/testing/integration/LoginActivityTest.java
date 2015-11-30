package com.dragan.android.testing.integration;

import com.dragan.android.testing.LoginActivity;
import com.dragan.android.testing.NavigationDrawerActivity;
import com.dragan.android.testing.R;

import android.app.Instrumentation;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.test.ViewAsserts;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * <a href="http://developer.android.com/training/activity-testing/index.html">Testing Your Activity</a>
 */
public class LoginActivityTest extends ActivityInstrumentationTestCase2<LoginActivity> {

    public static final String EMAIL = "foo@example.com";

    public static final String PASSWORD = "hello";

    private Context mContext;

    private LoginActivity mLoginActivity;

    private Button mLoginButton;

    private AutoCompleteTextView mEmail;

    private EditText mPassword;

    public LoginActivityTest() {
        super(LoginActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mContext = InstrumentationRegistry.getTargetContext();
        assertNotNull(mContext);

        mLoginActivity = getActivity();
        mLoginButton = (Button) mLoginActivity.findViewById(R.id.email_sign_in_button);
        mEmail = (AutoCompleteTextView) mLoginActivity.findViewById(R.id.email);
        mPassword = (EditText) mLoginActivity.findViewById(R.id.password);
    }

    public void testLoginResources() {
        assertEquals(mContext.getString(R.string.prompt_email), "Email");
        assertEquals(mContext.getString(R.string.prompt_password), "Password (optional)");
        assertEquals(mContext.getString(R.string.action_sign_in), "Sign in or register");
        assertEquals(mContext.getString(R.string.action_sign_in_short), "Sign in");
        assertEquals(mContext.getString(R.string.error_invalid_email), "This email address is invalid");
        assertEquals(mContext.getString(R.string.error_invalid_password), "This password is too short");
        assertEquals(mContext.getString(R.string.error_incorrect_password), "This password is incorrect");
        assertEquals(mContext.getString(R.string.error_field_required), "This field is required");
    }

    public void testLoginButtonLayout() {
        //Retrieve the top-level window decor view
        final View decorView = mLoginActivity.getWindow().getDecorView();

        //Verify that the mLoginButton is on screen
        ViewAsserts.assertOnScreen(decorView, mLoginButton);

        //Verify width and heights
        final ViewGroup.LayoutParams layoutParams = mLoginButton.getLayoutParams();
        assertNotNull(layoutParams);
        assertEquals(layoutParams.width, WindowManager.LayoutParams.MATCH_PARENT);
        assertEquals(layoutParams.height, WindowManager.LayoutParams.WRAP_CONTENT);
    }

    public void testLoginSuccess() {

        //Create and add an ActivityMonitor to monitor interaction between the system and the
        //NavigationDrawerActivity
        Instrumentation.ActivityMonitor navDrawerActivityMonitor = getInstrumentation()
                .addMonitor(NavigationDrawerActivity.class.getName(), null, false);

        // Send string input value
        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                mEmail.requestFocus();
            }
        });
        getInstrumentation().waitForIdleSync();
        getInstrumentation().sendStringSync(EMAIL);
        getInstrumentation().waitForIdleSync();

        // Send string input value
        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                mPassword.requestFocus();
            }
        });
        getInstrumentation().waitForIdleSync();
        getInstrumentation().sendStringSync(PASSWORD);
        getInstrumentation().waitForIdleSync();

        //Perform a click on mLoginButton
        TouchUtils.clickView(this, mLoginButton);

        //Wait until NavigationDrawerActivity was launched and get a reference to it.
        NavigationDrawerActivity navDrawerActivity = (NavigationDrawerActivity) navDrawerActivityMonitor.waitForActivityWithTimeout(5);

        //Verify that NavigationDrawerActivity was started
        assertNotNull("NavigationDrawerActivity is null", navDrawerActivity);
        assertEquals("Monitor for NavigationDrawerActivity has not been called", 1, navDrawerActivityMonitor.getHits());
        assertEquals("Activity is of wrong type", NavigationDrawerActivity.class, navDrawerActivity.getClass());

        //Verify the welcome text
        final TextView welcomeText = (TextView) navDrawerActivity.findViewById(R.id.text);
        assertNotNull(welcomeText);
        assertEquals("Wrong welcome message", "Welcome!", welcomeText.getText().toString());

        //Unregister monitor for NavigationDrawerActivity
        getInstrumentation().removeMonitor(navDrawerActivityMonitor);
    }
}
