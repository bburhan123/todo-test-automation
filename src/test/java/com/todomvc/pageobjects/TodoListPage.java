package com.todomvc.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import com.todomvc.utils.UiUtils;

import java.util.ArrayList;
import java.util.List;

public class TodoListPage {

    private final WebDriver driver;
    private final UiUtils uiUtils;

    @FindBy(id = "todo-input")
    private WebElement txtNewTodo;

    @FindBy(id = "toggle-all")
    private WebElement btnToggleAll;

    @FindBy(xpath = "//li[@data-testid='todo-item' and @class='']")
    //@FindBy(css = "li[data-testid='todo-item']:not(.completed)")
    private List<WebElement> lstTodoItems;

    //@FindBy(xpath = "//li[@data-testid='todo-item' and @class='completed']")
    @FindBy(css = "li[data-testid='todo-item'].completed")
    private List<WebElement> lstCompletedItems;

    @FindBy(css = "li[data-testid='todo-item']")
    private List<WebElement> lstAllItems;

    @FindBy(className = "todo-count")
    private WebElement lblTodoCount;

    @FindBy(xpath = "//a[text()='All']")
    private WebElement lnkAllFilter;

    @FindBy(xpath = "//a[text()='Active']")
    private WebElement lnkActiveFilter;

    @FindBy(xpath = "//a[text()='Completed']")
    private WebElement lnkCompletedFilter;

    @FindBy(className = "clear-completed")
    private WebElement btnClearCompleted;


    String btnTodoItemToggle = "//label[text()='%s']/preceding-sibling::input[@data-testid='todo-item-toggle']";
    String btnTodoItemDestroy = "//label[text()='%s']/following-sibling::button[@data-testid='todo-item-button']";


    public TodoListPage(WebDriver driver) {
        this.driver = driver;
        this.uiUtils = new UiUtils();
        PageFactory.initElements(driver, this);
    }

    public void verifyTodoItemsCount(int expectedCount) {
        int actualCount = lstTodoItems.size();
        Assert.assertEquals(actualCount, expectedCount);
    }

    public void addNewTodo(String todoText) {
        uiUtils.sendKeysAndPressEnter(txtNewTodo, todoText);
    }

    public int getAllItemsCount() {
        return lstAllItems.size();
    }

    public List<String> getTodoItems() {
        List<String> items = new ArrayList<>();
        for (WebElement element : lstTodoItems) {
            items.add(element.getText().trim());
        }
        return items;
    }

    public String getTodoCountText() {
        return lblTodoCount.getText();
    }

    public void clickTodoItemToggleByText(String text) {
        driver.findElement(By.xpath(String.format(btnTodoItemToggle, text))).click();
    }

    public boolean isItemCompleted(String text) {
        for (WebElement element : lstCompletedItems) {
            if (element.getText().trim().equals(text))
                return true;
        }
        return false;
    }

    public boolean isItemExists(String text) {
        for (WebElement element : lstAllItems) {
            if (element.getText().trim().equals(text))
                return true;
        }
        return false;
    }

    public void editTodoItemByText(String searchText, String editText) {
        for (WebElement element : lstTodoItems) {
            if (element.getText().trim().equalsIgnoreCase(searchText)) {
                WebElement lblElement = element.findElement(By.xpath(".//child::label"));
                uiUtils.doubleClick(lblElement);
                WebElement inputElement = element.findElement(By.xpath(".//input"));
                uiUtils.clearAndSendKeys(inputElement, editText);
                inputElement.sendKeys(Keys.ENTER);
                break;
            }
        }
    }

    public void removeItemByDestroyBtn(String text) {
        WebElement destroyBtn = driver.findElement(By.xpath(String.format(btnTodoItemDestroy, text)));
        uiUtils.clickByJS(destroyBtn);
    }

    public void clickFilter(String filter) {
        switch (filter) {
            case "All" -> lnkAllFilter.click();
            case "Active" -> lnkActiveFilter.click();
            case "Completed" -> lnkCompletedFilter.click();
        }
    }

    public void clickClearCompletedBtn() {
        btnClearCompleted.click();
    }

    public void clickToggleAllBtn() {
        btnToggleAll.click();
    }
}
