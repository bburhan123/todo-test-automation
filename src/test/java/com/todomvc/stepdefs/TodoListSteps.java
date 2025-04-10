package com.todomvc.stepdefs;

import com.todomvc.config.ConfigManager;
import com.todomvc.hooks.CucumberHooks;
import com.todomvc.utils.*;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import com.todomvc.pageobjects.TodoListPage;

import java.util.ArrayList;
import java.util.List;

public class TodoListSteps {
    WebDriver driver;
    Scenario scenario;
    List<String> addedTodos = new ArrayList<>();

    private TodoListPage todoListPage;

    UiUtils uiUtils;


    public TodoListSteps() {
        driver = CucumberHooks.driver.get();
        scenario = CucumberHooks.scenario;

        todoListPage = new TodoListPage(driver);
        uiUtils = new UiUtils();
    }

    @Given("the user navigates to todo list page")
    public void navigateTodoListPage() {
        String url = ConfigManager.getConfig().get("appUrl").toString();
        driver.get(url);
    }

    @Given("I am on an empty todo list page")
    public void iAmOnAnEmptyTodoListPage() {
        navigateTodoListPage();
        todoListPage.verifyTodoItemsCount(0);
        uiUtils.attachScreenShot();
    }

    @When("I add the following todo items")
    public void IAddTheFollowingTodoItems(List<String> todoItems) {
        for (String item : todoItems) {
            addedTodos.add(item);
            todoListPage.addNewTodo(item);
        }
        uiUtils.attachScreenShot();
    }

    @Then("the todo list should display the added items in the same order")
    public void theTodoListShouldDisplayTheAddedItems() {
        List<String> actualTodos = todoListPage.getTodoItems();
        Assert.assertEquals(actualTodos, addedTodos);
        uiUtils.attachScreenShot();
    }

    @And("the todo count should display {string}")
    public void theTodoCountShouldDisplay(String todoCount) {
        Assert.assertEquals(todoListPage.getTodoCountText(), todoCount);
        uiUtils.attachScreenShot();
    }

    @Given("I have the following items in the todo list")
    public void iHaveTheFollowingItemsInTheTodoList(List<String> todoItems) {
        navigateTodoListPage();
        for (String item : todoItems) {
            addedTodos.add(item);
            todoListPage.addNewTodo(item);
        }
        uiUtils.attachScreenShot();
    }

    @Given("I have the following completed items in the todo list")
    public void iHaveTheFollowingCompletedItemsInTheTodoList(List<String> todoItems) {
        iHaveTheFollowingItemsInTheTodoList(todoItems);
        todoListPage.clickToggleAllBtn();
        uiUtils.attachScreenShot();
    }

    @When("I mark the item {string} as completed")
    public void iMarkTheItemAsCompleted(String todoItem) {
        todoListPage.clickTodoItemToggleByText(todoItem);
        uiUtils.attachScreenShot();
    }

    @Then("the todo list should display {string} as completed")
    public void theTodoListShouldDisplayAsCompleted(String completedItem) {
        Assert.assertTrue(todoListPage.isItemCompleted(completedItem));
        uiUtils.attachScreenShot();
    }

    @When("I edit the item {string} to {string}")
    public void iEditTheItemTo(String existingText, String editText) {
        todoListPage.editTodoItemByText(existingText, editText);
        uiUtils.attachScreenShot();
    }

    @Then("the todo list should display the edited item {string}")
    public void theTodoListShouldDisplayTheEditedItem(String todoItem) {
        Assert.assertTrue(todoListPage.isItemExists(todoItem));
        uiUtils.attachScreenShot();
    }

    @When("I remove an active item {string}")
    @When("I remove a completed item {string}")
    public void iRemoveAItem(String itemText) {
        todoListPage.removeItemByDestroyBtn(itemText);
        uiUtils.attachScreenShot();
    }

    @Then("the todo list should not display {string}")
    public void theTodoListShouldNotDisplay(String item) {
        Assert.assertFalse(todoListPage.isItemExists(item));
        uiUtils.attachScreenShot();
    }

    @When("I select {string} filter")
    public void iSelectFilter(String filter) {
        todoListPage.clickFilter(filter);
        uiUtils.attachScreenShot();
    }

    @Then("the todo list should display all/only {int} item(s)")
    @Then("the todo list should display only {int} active/completed item(s)")
    public void theTodoListShouldDisplayItems(int itemCount) {
        Assert.assertEquals(todoListPage.getAllItemsCount(), itemCount);
        uiUtils.attachScreenShot();
    }

    @When("I clear all completed items using clear completed button")
    public void iClearAllCompletedItemsUsingClearCompletedButton() {
        todoListPage.clickClearCompletedBtn();
        uiUtils.attachScreenShot();
    }

    @When("I change the item {string} from completed to active")
    public void iChangeTheItemFromCompletedToActive(String item) {
        todoListPage.clickTodoItemToggleByText(item);
        uiUtils.attachScreenShot();
    }

    @When("I change all active items to completed using toggle-all button")
    @When("I change all Completed items to active using toggle-all button")
    public void iChangeAllItemsStatusUsingToggleAllButton() {
        todoListPage.clickToggleAllBtn();
        uiUtils.attachScreenShot();
    }
}