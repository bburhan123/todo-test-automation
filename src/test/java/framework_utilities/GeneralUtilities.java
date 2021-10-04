package framework_utilities;

import io.cucumber.java.Scenario;
import org.openqa.selenium.WebDriver;

public class GeneralUtilities {
    public WebDriver driver;
    public Scenario scenario;

    public GeneralUtilities() {
        driver = CucumberHooks.driver.get();
        scenario = CucumberHooks.scenario;
    }

    public boolean isStrEquals(String str1, String str2) {
        boolean result = false;
        if (str1 != null && str2 != null)
            result = str1.equalsIgnoreCase(str2);
        return result;
    }
}
