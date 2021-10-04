package framework_utilities;

import io.cucumber.java.Scenario;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

import page_objects.TodoList_PageObjects;


public class UiUtilities {
    public Scenario scenario;
    public WebDriver driver;
    GeneralUtilities generalUtilitiesObj = new GeneralUtilities();

    public UiUtilities() {
        driver = CucumberHooks.driver.get();
        scenario = CucumberHooks.scenario;
    }

    public void embedScreenShot() {
        scenario.embed(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES), "image/png","");
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
            WebDriverWait wait = new WebDriverWait(driver, 10);
            wait.until(ExpectedConditions.visibilityOfElementLocated(by));
            return true;
        } catch (NoSuchElementException ex) {
            return false;
        }
    }

    public int getElementCount(By by) {
        int result = 0;
        try {
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            result = driver.findElements(by).size();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        }
        return result;
    }

    public boolean verifyTextInElement(By by, String text) {
        boolean result = false;
        WebElement webElement = driver.findElement(by);
        if (generalUtilitiesObj.isStrEquals(webElement.getText(), text))
            result = true;
        return result;
    }

    public boolean verifyItemFromTodoList(String searchText) {
        boolean result = false;
        String textFromItem;
        List<WebElement> items = driver.findElements(TodoList_PageObjects.lst_TodoItems);
        for (WebElement item : items) {
            textFromItem = item.getText().trim();
            if (generalUtilitiesObj.isStrEquals(searchText.trim(), textFromItem)) {
                result = true;
                break;
            }
        }
        return result;
    }

    public boolean verifyCompletedItemFromList(String searchText) {
        boolean result = false;
        String textFromItem;
        List<WebElement> items = driver.findElements(TodoList_PageObjects.lst_CompletedItems);
        for (WebElement item : items) {
            textFromItem = item.getText().trim();
            if (generalUtilitiesObj.isStrEquals(searchText.trim(), textFromItem)) {
                result = true;
                break;
            }
        }
        return result;
    }

    public void checkItemOnTodoList(String searchText) {
        String textFromItem;
        List<WebElement> items = driver.findElements(TodoList_PageObjects.lst_AllItems);
        for (WebElement item : items) {
            textFromItem = item.getText().trim();
            if (generalUtilitiesObj.isStrEquals(searchText.trim(), textFromItem)) {
                WebElement checkbox = item.findElement(By.xpath(".//child::input[@class='toggle']"));
                checkbox.click();
                break;
            }
        }
    }

    public void clearItemUsingDestroyBtn(String searchText) {
        String textFromItem;
        List<WebElement> items = driver.findElements(TodoList_PageObjects.lst_AllItems);
        for (WebElement item : items) {
            textFromItem = item.getText().trim();
            if (generalUtilitiesObj.isStrEquals(searchText.trim(), textFromItem)) {
                WebElement destroyBtn = item.findElement(By.xpath(".//child::button[@class='destroy']"));
                clickByJS(destroyBtn);
                break;
            }
        }
    }

    public void editItemOnTodoList(String searchText, String editText) {
        String textFromItem;
        List<WebElement> items = driver.findElements(TodoList_PageObjects.lst_AllItems);
        for (WebElement item : items) {
            textFromItem = item.getText().trim();
            if (generalUtilitiesObj.isStrEquals(searchText.trim(), textFromItem)) {
                WebElement lblElement = item.findElement(By.xpath(".//child::label"));
                doubleClick(lblElement);
                WebElement inputElement = item.findElement(By.xpath(".//child::input[@class='edit']"));
                clearAndSendKeys(inputElement,editText);
                inputElement.sendKeys(Keys.ENTER);
                break;
            }
        }
    }
}
