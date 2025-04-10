package com.todomvc.hooks;

import com.todomvc.config.ConfigManager;
import com.todomvc.driver.DriverFactory;
import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.time.Instant;

public class CucumberHooks {
    public static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    public static Scenario scenario;

    @Before(order = 0)
    public void initializeSetup(Scenario scenario) throws Exception {
        System.out.println(Instant.now().toString() + " Initializing the Config setup");
        ConfigManager.LoadConfig();
        CucumberHooks.scenario = scenario;
    }

    @Before(order = 1)
    public void initializeWebDriver() throws Exception {
        System.out.println(Instant.now().toString() + " Initializing the web driver");
        System.out.println("Browser name - " + ConfigManager.getConfig().get("browser"));

        driver.set(DriverFactory.createWebDriver());
    }


    @After(order = 0)
    public void cleanupSetup() {
        try {
            if (scenario.isFailed()) {
                byte[] screenshot = ((TakesScreenshot) driver.get()).getScreenshotAs(OutputType.BYTES);
                scenario.attach(screenshot, "image/png", "Screenshot");
            }
            driver.get().quit();
            driver.remove();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //todo- Somehow , its not attaching the screenshot in default html report
    /*@AfterStep(order = 0)
    public void attachScreenshotAfterStep() {
        byte[] screenshot = ((TakesScreenshot) driver.get()).getScreenshotAs(OutputType.BYTES);
        scenario.attach(screenshot, "image/png", "Screenshot");
    }*/
}