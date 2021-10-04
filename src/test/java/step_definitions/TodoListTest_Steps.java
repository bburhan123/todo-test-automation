package step_definitions;

import com.google.gson.JsonObject;
import framework_utilities.CucumberHooks;
import framework_utilities.GeneralUtilities;
import framework_utilities.TestDataReader;
import framework_utilities.UiUtilities;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.html5.LocalStorage;
import org.openqa.selenium.html5.WebStorage;
import org.testng.Assert;
import page_objects.TodoList_PageObjects;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

public class TodoListTest_Steps {

    public WebDriver driver;
    public Scenario scenario;
    public JsonObject testDataJsonObj;

    TestDataReader testDataReaderObj = new TestDataReader();
    UiUtilities uiUtilitiesObj = new UiUtilities();
    GeneralUtilities generalUtilitiesObj = new GeneralUtilities();

    public TodoListTest_Steps() {
        driver = CucumberHooks.driver.get();
        scenario = CucumberHooks.scenario;
        testDataJsonObj = CucumberHooks.testDataJsonObj;
    }

    @Given("User is on empty todo list page")
    public void userIsOnEmptyTodoListPage() throws MalformedURLException {
        String url = testDataJsonObj.get("ApplicationUrl").getAsString();
        final URL appURL = new URL(url);
        driver.get(appURL.toString());
        Assert.assertTrue(uiUtilitiesObj.isElementDisplayed(TodoList_PageObjects.input_NewTodo));
        uiUtilitiesObj.embedScreenShot();
    }

    @When("User adds the following todo item")
    public void userAddsTheFollowingTodoItem(DataTable table) {
        String newItem;
        for (Map<String, String> data : table.asMaps()) {
            newItem = testDataReaderObj.readData(data.get("<NewItem>"));

            if (newItem != null)
                uiUtilitiesObj.sendKeysAndPressEnter(TodoList_PageObjects.input_NewTodo, newItem);
        }
        uiUtilitiesObj.embedScreenShot();
    }

    @Then("User should see added item in todo list")
    public void userShouldSeeAddedItemInTodoList(DataTable table) {
        String itemText;
        for (Map<String, String> data : table.asMaps()) {
            itemText = testDataReaderObj.readData(data.get("<AddedItem>"));

            if (itemText != null)
                Assert.assertTrue(uiUtilitiesObj.verifyItemFromTodoList(itemText));
        }
        uiUtilitiesObj.embedScreenShot();
    }

    @And("Todo count should show {string}")
    public void todoCountShouldShow(String noOfItemsLeft) {
        Assert.assertTrue(uiUtilitiesObj.verifyTextInElement(TodoList_PageObjects.lbl_TodoCount, noOfItemsLeft));
        uiUtilitiesObj.embedScreenShot();
    }

    @Given("User has 3 {string} items in todo list")
    public void userHasItemsInTodoList(String itemStatus) throws MalformedURLException {
        String url = testDataJsonObj.get("ApplicationUrl").getAsString();
        final URL appURL = new URL(url);
        driver.get(appURL.toString());
        Assert.assertTrue(uiUtilitiesObj.isElementDisplayed(TodoList_PageObjects.input_NewTodo));
        LocalStorage local = ((WebStorage) driver).getLocalStorage();

        if (generalUtilitiesObj.isStrEquals(itemStatus,"active")) {
            local.setItem("todos-vuejs", "[{\"id\": 1,\"title\": \"Write gherkin scenarios\",\"completed\": false},{\"id\": 2,\"title\": \"Implement step definitions\",\"completed\": false},{\"id\": 3,\"title\": \"Execute scenarios\",\"completed\": false}]");
            driver.navigate().refresh();
            Assert.assertTrue(uiUtilitiesObj.verifyTextInElement(TodoList_PageObjects.lbl_TodoCount, "3 items left"));
        } else if (generalUtilitiesObj.isStrEquals(itemStatus,"completed")) {
            local.setItem("todos-vuejs", "[{\"id\": 1,\"title\": \"Write gherkin scenarios\",\"completed\": true},{\"id\": 2,\"title\": \"Implement step definitions\",\"completed\": true},{\"id\": 3,\"title\": \"Execute scenarios\",\"completed\": true}]");
            driver.navigate().refresh();
            Assert.assertTrue(uiUtilitiesObj.verifyTextInElement(TodoList_PageObjects.lbl_TodoCount, "0 items left"));
        }
        uiUtilitiesObj.embedScreenShot();
    }

    @When("User marks the following item as completed")
    public void userMarksTheFollowingItemAsCompleted(DataTable table) {
        String itemText;
        for (Map<String, String> data : table.asMaps()) {
            itemText = testDataReaderObj.readData(data.get("<Item>"));

            if (itemText != null)
                uiUtilitiesObj.checkItemOnTodoList(itemText);
        }
        uiUtilitiesObj.embedScreenShot();
    }

    @Then("User should see strikeout on completed item text")
    public void userShouldSeeStrikeoutOnCompletedItemText(DataTable table) {
        String itemText;
        for (Map<String, String> data : table.asMaps()) {
            itemText = testDataReaderObj.readData(data.get("<CompletedItem>"));

            if (itemText != null)
                Assert.assertTrue(uiUtilitiesObj.verifyCompletedItemFromList(itemText));
        }
        uiUtilitiesObj.embedScreenShot();
    }

    @When("User marks all items as completed using toggle-all")
    public void userMarksAllItemsAsCompletedUsingToggleAll() {
        uiUtilitiesObj.click(TodoList_PageObjects.lbl_ToggleAll);
        uiUtilitiesObj.embedScreenShot();
    }

    @Then("^User should see (\\d+) (?:all|active|completed) items in the list.*$")
    public void userShouldSeeOnlyCompletedItemsInTheList(int noOfCompletedItems) {
        Assert.assertEquals(uiUtilitiesObj.getElementCount(TodoList_PageObjects.lst_AllItems), noOfCompletedItems);
        uiUtilitiesObj.embedScreenShot();
    }

    @When("User selects {string} filter")
    public void userSelectsFilter(String filter) {
        switch (filter) {
            case ("All"):
                uiUtilitiesObj.click(TodoList_PageObjects.lnk_AllFilter);
                break;
            case ("Active"):
                uiUtilitiesObj.click(TodoList_PageObjects.lnk_ActiveFilter);
                break;
            case ("Completed"):
                uiUtilitiesObj.click(TodoList_PageObjects.lnk_CompletedFilter);
                break;
        }
        uiUtilitiesObj.embedScreenShot();
    }

    @When("User clears the following item using destroy button")
    public void userClearsTheFollowingItemUsingDestroyButton(DataTable table) {
        String itemText;
        for (Map<String, String> data : table.asMaps()) {
            itemText = testDataReaderObj.readData(data.get("<Item>"));

            if (itemText != null)
                uiUtilitiesObj.clearItemUsingDestroyBtn(itemText);
        }
        uiUtilitiesObj.embedScreenShot();
    }

    @When("User clears all completed items using clear completed button")
    public void userClearsAllCompletedItemsUsingClearCompletedButton() {
        uiUtilitiesObj.click(TodoList_PageObjects.btn_ClearCompleted);
        uiUtilitiesObj.embedScreenShot();
    }

    @When("User changes the following item from completed to active")
    public void userChangesTheFollowingItemFromCompletedToActive(DataTable table) {
        String itemText;
        for (Map<String, String> data : table.asMaps()) {
            itemText = testDataReaderObj.readData(data.get("<Item>"));

            if (itemText != null)
                uiUtilitiesObj.checkItemOnTodoList(itemText);
        }
        uiUtilitiesObj.embedScreenShot();
    }

    @When("User changes all items from completed to active")
    public void userChangesAllItemsFromCompletedToActive() {
        uiUtilitiesObj.click(TodoList_PageObjects.lbl_ToggleAll);
        uiUtilitiesObj.embedScreenShot();
    }

    @When("User edit the text of the following item")
    public void userEditTheTextOfTheFollowingItem(DataTable table) {
        String originalText,editedText ;
        for (Map<String, String> data : table.asMaps()) {
            originalText = testDataReaderObj.readData(data.get("<OriginalText>"));
            editedText = testDataReaderObj.readData(data.get("<EditedText>"));

            if (originalText != null)
                uiUtilitiesObj.editItemOnTodoList(originalText, editedText);
        }
        uiUtilitiesObj.embedScreenShot();
    }

    @Then("User should see edited item in todo list")
    public void userShouldSeeEditedItemInTodoList(DataTable table) {
        String itemText;
        for (Map<String, String> data : table.asMaps()) {
            itemText = testDataReaderObj.readData(data.get("<EditedItem>"));

            if (itemText != null)
                Assert.assertTrue(uiUtilitiesObj.verifyItemFromTodoList(itemText));
        }
        uiUtilitiesObj.embedScreenShot();
    }
}
