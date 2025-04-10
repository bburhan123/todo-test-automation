package com.todomvc.utils;

import io.cucumber.java.Scenario;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;


public class UiUtils {
    public Scenario scenario;
    public WebDriver driver;

    public UiUtils() {
        driver = CucumberHooks.driver.get();
        scenario = CucumberHooks.scenario;
    }

    public boolean isElementDisplayed(WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        return wait.until(ExpectedConditions.visibilityOf(element)).isDisplayed();
    }

    public void sendKeysAndPressEnter(WebElement element, String text) {
        element.sendKeys(text);
        element.sendKeys(Keys.ENTER);
    }

    public void attachScreenShot() {
        scenario.attach(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES), "image/png","Screenshot");
    }

    public void click(By by) {
        WebElement webElement = driver.findElement(by);
        webElement.click();
    }

    public void doubleClick(WebElement webElement) {
        Actions actions = new Actions(driver);
        actions.doubleClick(webElement).perform();
    }

    public void clickByJS(WebElement webElement) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", webElement);
    }

    public void sendKeysAndPressEnter(By by, String str) {
        WebElement webElement = driver.findElement(by);
        webElement.sendKeys(str);
        webElement.sendKeys(Keys.ENTER);
    }

    public void clearAndSendKeys(WebElement webElement, String str) {
        webElement.sendKeys(Keys.CONTROL + "a");
        webElement.sendKeys(Keys.DELETE);
        webElement.sendKeys(str);
    }

    public boolean isElementDisplayed(By by) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOfElementLocated(by));
            return true;
        } catch (NoSuchElementException ex) {
            return false;
        }
    }
}