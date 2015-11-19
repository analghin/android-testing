package com.dragan.android.testing.ui.uiautomator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.Direction;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiScrollable;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;
import android.widget.TextView;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class UiAutomatorTest {

    /**
     * The target app package.
     */
    private static final String TARGET_PACKAGE =
            InstrumentationRegistry.getTargetContext().getPackageName();

    /**
     * The timeout to start the target app.
     */
    private static final int LAUNCH_TIMEOUT = 5000;

    public static final int WAIT_TIMEOUT = 5000;

    public static final int SPEED_2K_PIXELS_PER_SECOND = 2000;

    private UiDevice mDevice;

    @Before
    public void startLoginActivityFromHomeScreen() {
        // Initialize UiDevice instance
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());

        // Start from the home screen
        mDevice.pressHome();

        // Wait for launcher
        final String launcherPackage = getLauncherPackageName();
        assertThat(launcherPackage, notNullValue());
        mDevice.wait(Until.hasObject(By.pkg(launcherPackage).depth(0)), LAUNCH_TIMEOUT);

        // Launch the demo app
        Context context = InstrumentationRegistry.getContext();
        final Intent intent = context.getPackageManager().getLaunchIntentForPackage(TARGET_PACKAGE);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);    // Clear out any previous instances
        context.startActivity(intent);

        // Wait for the app to appear
        mDevice.wait(Until.hasObject(By.pkg(TARGET_PACKAGE).depth(0)), LAUNCH_TIMEOUT);
    }

    @Test
    public void doSuccessLoginAndCheckTheWelcomeText() {
        UiObject2 emaiObject = mDevice.findObject(By.desc("An edit text for email"));
        emaiObject.setText("email@endava.com");
        UiObject2 passwordObject = mDevice.findObject(By.desc("An edit text for password"));
        passwordObject.setText("email");

        mDevice.findObject(By.text("Sign in or register")).click();

        UiObject2 welcomeTextView = mDevice.wait(Until.findObject(By.text("Welcome!")), WAIT_TIMEOUT);
        assertThat(welcomeTextView.getText(), is(equalTo("Welcome!")));
    }

    @Test
    public void findViewPerformActionAndCheckAssertion() throws UiObjectNotFoundException {
        UiObject2 emaiObject = mDevice.findObject(By.desc("An edit text for email"));
        emaiObject.setText("email@endava.com");
        UiObject2 passwordObject = mDevice.findObject(By.desc("An edit text for password"));
        passwordObject.setText("email");

        mDevice.findObject(By.text("Sign in or register")).click();

        UiObject2 welcomeTextView = mDevice.wait(Until.findObject(By.text("Welcome!")), WAIT_TIMEOUT);
        assertThat(welcomeTextView.getText(), is(equalTo("Welcome!")));

        mDevice.wait(Until.findObject(By.desc("Open navigation drawer")), WAIT_TIMEOUT).click();
        mDevice.wait(Until.findObject(By.text("Slideshow")), WAIT_TIMEOUT).click();

        UiObject2 scrollableActivity = mDevice.wait(Until.findObject(By.desc("A nested scroll view")), WAIT_TIMEOUT);
        scrollableActivity.swipe(Direction.UP, 1f, SPEED_2K_PIXELS_PER_SECOND);
        scrollableActivity.swipe(Direction.UP, 1f, SPEED_2K_PIXELS_PER_SECOND);

        mDevice.pressBack();

        mDevice.wait(Until.findObject(By.desc("Open navigation drawer")), WAIT_TIMEOUT).click();
        mDevice.wait(Until.findObject(By.text("Import")), WAIT_TIMEOUT).click();

        mDevice.wait(Until.findObject(By.desc("A list with items")), WAIT_TIMEOUT);
        UiScrollable listView = new UiScrollable(new UiSelector());
        listView.setMaxSearchSwipes(100).scrollTextIntoView("Item 25");
        listView.getChildByText(new UiSelector().className(TextView.class.getName()), "Item 25").click();

        final UiObject textContent =new UiObject(new UiSelector().className(TextView.class).textContains("Details about Item: 25"));
        assertThat(textContent.getText(), containsString("Details about Item: 25"));
        final UiObject2 scrollableItem = mDevice.wait(Until.findObject(By.scrollable(true)), WAIT_TIMEOUT);
        scrollableItem.swipe(Direction.UP, 1f, SPEED_2K_PIXELS_PER_SECOND);
        scrollableItem.swipe(Direction.DOWN, 1f, SPEED_2K_PIXELS_PER_SECOND);

        mDevice.pressBack();
        mDevice.pressBack();

        UiObject2 welcomeTextView2 = mDevice.wait(Until.findObject(By.text("Welcome!")), WAIT_TIMEOUT);
        assertThat(welcomeTextView2.getText(), is(equalTo("Welcome!")));
    }

    /**
     * Uses package manager to find the package name of the device launcher. Usually this package
     * is "com.android.launcher" but can be different at times. This is a generic solution which
     * works on all platforms.
     */
    private String getLauncherPackageName() {
        // Create launcher Intent
        final Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);

        // Use PackageManager to get the launcher package name
        PackageManager pm = InstrumentationRegistry.getContext().getPackageManager();
        ResolveInfo resolveInfo = pm.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return resolveInfo.activityInfo.packageName;
    }
}
