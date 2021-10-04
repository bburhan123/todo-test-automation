package page_objects;

import org.openqa.selenium.By;

public class TodoList_PageObjects {
    /******************************************** Button Elements ********************************************************************/
    public static final By btn_ClearCompleted = By.className("clear-completed");

    /******************************************** Input Elements ********************************************************************/
    public static final By input_NewTodo = By.xpath("//input[@class='new-todo']");
    public static final By input_EditTodo = By.xpath("//input[@class='edit']");

    /******************************************** Label Elements ********************************************************************/
    public static final By lbl_TodoCount = By.className("todo-count");
    public static final By lbl_ToggleAll = By.xpath("//label[@for='toggle-all']");

    /******************************************** Link Elements ********************************************************************/
    public static final By lnk_AllFilter = By.xpath("//a[@href ='#/all']");
    public static final By lnk_ActiveFilter = By.xpath("//a[@href ='#/active']");
    public static final By lnk_CompletedFilter = By.xpath("//a[@href ='#/completed']");

    /******************************************** List Elements ********************************************************************/
    public static final By lst_TodoItems = By.xpath("//ul[@class='todo-list']/li[@class='todo']");
    public static final By lst_CompletedItems = By.xpath("//ul[@class='todo-list']/li[@class='todo completed']");
    public static final By lst_AllItems = By.xpath("//ul[@class='todo-list']/li");
}
