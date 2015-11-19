package com.dragan.android.testing;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.net.URL;

import io.appium.java_client.android.AndroidDriver;

import static com.dragan.android.testing.Helpers.driver;
import static com.dragan.android.testing.Helpers.find;
import static com.dragan.android.testing.Helpers.forFind;
import static com.dragan.android.testing.Helpers.forId;
import static com.dragan.android.testing.Helpers.init;

public class AppiumTest {
    /** Run before each test **/
    @Before
    public void setUp() throws Exception {
        final File classpathRoot = new File(System.getProperty("user.dir"));
        final File appDir = new File(classpathRoot, "build/outputs/apk");
        final File app = new File(appDir, "app-debug.apk");

        final DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("appium-version", "1.4.13.1");
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", "192.168.56.101:5555");
        capabilities.setCapability(CapabilityType.VERSION, "5.0");
        capabilities.setCapability("app", app.getAbsolutePath());

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
        init(driver);
    }

    @Test
    public void testSuccessLogin(){
        WebElement email = Helpers.wait(forId("email"));
        email.sendKeys("email@endava.com");
        WebElement password = Helpers.wait(forId("password"));
        password.click();
        password.sendKeys("email");
        find("Sign in or register").click();

        Helpers.wait(forFind("Welcome!"));
    }

    @Test
    public void testTheScrolling(){
        WebElement email = Helpers.wait(forId("email"));
        email.sendKeys("email@endava.com");
        WebElement password = Helpers.wait(forId("password"));
        password.click();
        password.sendKeys("email");
        find("Sign in or register").click();

        Helpers.wait(forFind("Welcome!"));

        Helpers.wait(forId("fab")).click();
        Helpers.wait(forFind("Open navigation drawer")).click();
        Helpers.back();
        Helpers.wait(forFind("More options")).click();
        Helpers.wait(forFind("Settings")).click();
        Helpers.wait(forFind("General")).click();
        Helpers.wait(forFind("Display name")).click();
        WebElement nameInput = Helpers.wait(forFind("John Smith"));
        nameInput.click();
        nameInput.sendKeys(" Jr");
        Helpers.wait(forFind("OK")).click();
        Helpers.wait(forFind("Jr"));
        Helpers.driver.closeApp();
    }

    /** Run after each test **/
    @After
    public void tearDown() throws Exception {
        if (driver != null) {
            driver.quit();
        }
    }
}
