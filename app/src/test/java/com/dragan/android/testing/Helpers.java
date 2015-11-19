package com.dragan.android.testing;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.appium.java_client.android.AndroidDriver;

public abstract class Helpers {

    public static AndroidDriver driver;

    public static URL serverAddress;

    private static WebDriverWait driverWait;

    /**
     * Initialize the webdriver. Must be called before using any helper methods. *
     */
    public static void init(AndroidDriver webDriver, URL driverServerAddress) {
        driver = webDriver;
        serverAddress = driverServerAddress;
        int timeoutInSeconds = 60;
        // must wait at least 60 seconds for running on Sauce.
        // waiting for 30 seconds works locally however it fails on Sauce.
        driverWait = new WebDriverWait(webDriver, timeoutInSeconds);
    }

    /**
     * Initialize the webdriver. Must be called before using any helper methods. *
     */
    public static void init(AndroidDriver webDriver) {
        driver = webDriver;
        // must wait at least 60 seconds for running on Sauce.
        // waiting for 30 seconds works locally however it fails on Sauce.
        driverWait = new WebDriverWait(webDriver, 60);
    }

    /**
     * Set implicit wait in seconds *
     */
    public static void setWait(int seconds) {
        driver.manage().timeouts().implicitlyWait(seconds, TimeUnit.SECONDS);
    }

    /**
     * Return an element by locator *
     */
    public static WebElement element(By locator) {
        return driver.findElement(locator);
    }

    /**
     * Return a list of elements by locator *
     */
    public static List<WebElement> elements(By locator) {
        return driver.findElements(locator);
    }

    /**
     * Press the back button *
     */
    public static void back() {
        driver.navigate().back();
    }

    /**
     * Return a list of elements by tag name *
     */
    public static List<WebElement> tags(String tagName) {
        return elements(forTags(tagName));
    }

    /**
     * Return a tag name locator *
     */
    public static By forTags(String tagName) {
        return By.className(tagName);
    }

    /**
     * Return a id *
     */
    public static By forId(String id) {
        return By.id(id);
    }

    /**
     * Return a static text element by xpath index *
     */
    public static WebElement textByIndex(int xpathIndex) {
        return element(forText(xpathIndex));
    }

    /**
     * Return a static text locator by xpath index *
     */
    public static By forText(int xpathIndex) {
        return By.xpath("//android.widget.TextView[" + xpathIndex + "]");
    }

    /**
     * Return a static text element that contains text *
     */
    public static WebElement text(String text) {
        return element(forText(text));
    }

    /**
     * Return a static text locator that contains text *
     */
    public static By forText(String text) {
        return By.xpath("//android.widget.TextView[contains(@text, '" + text + "')]");
    }

    /**
     * Return a static text element by exact text *
     */
    public static WebElement textExact(String text) {
        return element(forTextExact(text));
    }

    /**
     * Return a static text locator by exact text *
     */
    public static By forTextExact(String text) {
        return By.xpath("//android.widget.TextView[@text='" + text + "']");
    }

    public static By forFind(String value) {
        return By.xpath("//*[@content-desc=\"" + value + "\" or @resource-id=\"" + value +
                "\" or @text=\"" + value + "\"] | //*[contains(translate(@content-desc,\"" + value +
                "\",\"" + value + "\"), \"" + value + "\") or contains(translate(@text,\"" + value +
                "\",\"" + value + "\"), \"" + value + "\") or @resource-id=\"" + value + "\"]");
    }

    public static WebElement find(String value) {
        return element(forFind(value));
    }

    /**
     * Wait 30 seconds for locator to find an element *
     */
    public static WebElement wait(By locator) {
        return driverWait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /**
     * Wait 60 seconds for locator to find all elements *
     */
    public static List<WebElement> waitAll(By locator) {
        return driverWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }

    /**
     * Wait 60 seconds for locator to not find a visible element *
     */
    public static boolean waitInvisible(By locator) {
        return driverWait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    /**
     * Return an element that contains name or text *
     */
    public static WebElement scrollTo(String value) {
        return driver.scrollTo(value);
    }

    /**
     * Return an element that exactly matches name or text *
     */
    public static WebElement scrollToExact(String value) {
        return driver.scrollToExact(value);
    }
}